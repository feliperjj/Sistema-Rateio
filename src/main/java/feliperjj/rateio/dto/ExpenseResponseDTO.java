package feliperjj.rateio.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseResponseDTO(
        UUID id,
        String description,
        BigDecimal amount,
        String payerName,
        String eventName
) {
}