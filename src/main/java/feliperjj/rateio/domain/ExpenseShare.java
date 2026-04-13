package feliperjj.rateio.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "expense_shares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseShare {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owed_by_id", nullable = false)
    private User owedBy;

    @DecimalMin(value = "0.01", message = "A parcela devida deve ser maior que zero")
    @Column(name = "amount_owed", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountOwed;

}