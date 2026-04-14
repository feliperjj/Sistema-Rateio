package feliperjj.rateio.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "paid_by_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Expense() {}

    // Getters e Setters Manuais
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public User getPayer() { return payer; }
    public void setPayer(User payer) { this.payer = payer; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}