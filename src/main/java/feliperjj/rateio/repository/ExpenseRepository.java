package feliperjj.rateio.repository;

import feliperjj.rateio.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    
    List<Expense> findByEventId(UUID eventId);
    
}