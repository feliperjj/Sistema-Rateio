package feliperjj.rateio.dto;

import java.util.UUID;

public record ParticipantResponseDTO(
        UUID id,
        String eventName,
        String userName
) {
}