package feliperjj.rateio.repository;

import feliperjj.rateio.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    // Para procurar todas as despesas associadas a um evento
    List<Expense> findByEventId(UUID eventId);
}