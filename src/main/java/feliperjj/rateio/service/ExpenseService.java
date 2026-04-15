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

import java.util.UUID; // 👈 AQUI ESTÁ O IMPORT QUE FALTAVA!

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

    // Adicionamos o UUID payerId como parâmetro
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO dto, UUID payerId) {
        
        // Agora usamos o payerId seguro
        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        Expense expense = new Expense();
        expense.setDescription(dto.description());
        expense.setAmount(dto.amount());
        expense.setPayer(payer);
        expense.setEvent(event);

        Expense savedExpense = expenseRepository.save(expense);

        return new ExpenseResponseDTO(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getAmount(),
                payer.getName(),
                event.getName()
        );
    }
}