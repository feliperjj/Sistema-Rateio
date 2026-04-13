package feliperjj.rateio.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ParticipantRequestDTO(
        @NotNull(message = "O ID do evento é obrigatório")
        UUID eventId,
        
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID userId
) {
}