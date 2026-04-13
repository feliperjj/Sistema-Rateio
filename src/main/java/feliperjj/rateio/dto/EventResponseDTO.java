package feliperjj.rateio.dto;

import java.util.UUID;

public record EventResponseDTO(
        UUID id,
        String name,
        String description,
        UUID creatorId,
        String creatorName // É sempre boa prática devolver o nome para a UI mostrar "Criado por: Felipe"
) {}