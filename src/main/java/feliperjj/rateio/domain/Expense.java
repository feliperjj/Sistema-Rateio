package feliperjj.rateio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private BigDecimal amount;

    // A anotação @ManyToOne diz que "Muitas despesas podem pertencer a Um pagador"
    @ManyToOne
    @JoinColumn(name = "paid_by_id")
    private User payer;

    // A anotação @ManyToOne diz que "Muitas despesas podem pertencer a Um evento"
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}