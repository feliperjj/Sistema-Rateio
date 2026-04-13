package feliperjj.rateio.service;

import feliperjj.rateio.domain.Event;
import feliperjj.rateio.domain.Expense;
import feliperjj.rateio.domain.User;
import feliperjj.rateio.dto.ExpenseRequestDTO;
import feliperjj.rateio.dto.ExpenseResponseDTO;
import feliperjj.rateio.repository.EventRepository;
import feliperjj.rateio.repository.ExpenseRepository;
import feliperjj.rateio.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO dto) {
        // 1. Verificar se o utilizador (quem pagou) existe
        User payer = userRepository.findById(dto.payerId())
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        // 2. Verificar se o evento existe
        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        // 3. Criar a Despesa
        Expense expense = new Expense();
        expense.setDescription(dto.description());
        expense.setAmount(dto.amount());
        expense.setPayer(payer);
        expense.setEvent(event);

        // 4. Guardar na Base de Dados
        Expense savedExpense = expenseRepository.save(expense);

        // 5. Devolver os dados formatados
        return new ExpenseResponseDTO(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getAmount(),
                payer.getName(),
                event.getName()
        );
    }
}