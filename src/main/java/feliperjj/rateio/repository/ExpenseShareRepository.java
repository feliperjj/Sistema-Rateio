package feliperjj.rateio.repository;

import feliperjj.rateio.domain.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {
    // Para procurar como uma despesa específica foi dividida
    List<ExpenseShare> findByExpenseId(UUID expenseId);
}