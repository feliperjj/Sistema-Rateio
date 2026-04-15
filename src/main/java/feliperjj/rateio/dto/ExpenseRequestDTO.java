package feliperjj.rateio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseRequestDTO(
        @NotBlank(message = "A descrição da despesa é obrigatória")
        String description,
        
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal amount,
        
        // 🚨 O payerId FOI REMOVIDO DAQUI! 🚨
        
        @NotNull(message = "O ID do evento é obrigatório")
        UUID eventId
) {}