package feliperjj.rateio.service;

import feliperjj.rateio.domain.Expense;
import feliperjj.rateio.domain.Participant;
import feliperjj.rateio.dto.SettlementResponseDTO;
import feliperjj.rateio.repository.EventRepository;
import feliperjj.rateio.repository.ExpenseRepository;
import feliperjj.rateio.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SettlementService {

    private final EventRepository eventRepository;
    private final ExpenseRepository expenseRepository;
    private final ParticipantRepository participantRepository;

    public SettlementService(EventRepository eventRepository, ExpenseRepository expenseRepository, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.expenseRepository = expenseRepository;
        this.participantRepository = participantRepository;
    }

    public List<SettlementResponseDTO> calculateSettlement(UUID eventId) {
        // 1. Verifica se o evento existe
        if (!eventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Evento não encontrado.");
        }

        List<Participant> participants = participantRepository.findByEventId(eventId);
        List<Expense> expenses = expenseRepository.findByEventId(eventId);

        if (participants.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Calcula o total gasto e quanto cada um deveria pagar (Rateio igualitário)
        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal splitAmount = totalExpenses.divide(new BigDecimal(participants.size()), 2, RoundingMode.HALF_UP);

        // 3. Calcula o balanço de cada pessoa (Saldo = O que pagou - O que deveria pagar)
        Map<UUID, BigDecimal> balances = new HashMap<>();
        Map<UUID, String> userNames = new HashMap<>();

        for (Participant p : participants) {
            balances.put(p.getUser().getId(), splitAmount.negate()); // Todos começam devendo a sua parte
            userNames.put(p.getUser().getId(), p.getUser().getName());
        }

        for (Expense e : expenses) {
            UUID payerId = e.getPayer().getId();
            if (balances.containsKey(payerId)) {
                // Adiciona o valor que a pessoa pagou ao saldo dela
                balances.put(payerId, balances.get(payerId).add(e.getAmount()));
            }
        }

        // 4. Separa quem deve (negativo) de quem tem a receber (positivo)
        List<Map.Entry<UUID, BigDecimal>> debtors = balances.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.toList());

        List<Map.Entry<UUID, BigDecimal>> creditors = balances.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        // 5. Cruza os devedores com os credores para gerar os pagamentos
        List<SettlementResponseDTO> settlements = new ArrayList<>();
        int d = 0, c = 0;

        while (d < debtors.size() && c < creditors.size()) {
            Map.Entry<UUID, BigDecimal> debtor = debtors.get(d);
            Map.Entry<UUID, BigDecimal> creditor = creditors.get(c);

            BigDecimal debt = debtor.getValue().negate(); // Transforma dívida em número positivo para o cálculo
            BigDecimal credit = creditor.getValue();

            BigDecimal settledAmount = debt.min(credit); // Pega o menor valor entre a dívida e o crédito

            settlements.add(new SettlementResponseDTO(
                    userNames.get(debtor.getKey()),
                    userNames.get(creditor.getKey()),
                    settledAmount
            ));

            // Atualiza os saldos após essa "transferência"
            debtor.setValue(debtor.getValue().add(settledAmount));
            creditor.setValue(creditor.getValue().subtract(settledAmount));

            // Se a dívida zerou, passa para o próximo devedor
            if (debtor.getValue().compareTo(BigDecimal.ZERO) == 0) d++;
            // Se o crédito zerou, passa para o próximo credor
            if (creditor.getValue().compareTo(BigDecimal.ZERO) == 0) c++;
        }

        return settlements;
    }
}