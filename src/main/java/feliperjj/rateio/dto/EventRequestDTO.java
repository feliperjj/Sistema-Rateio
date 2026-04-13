package feliperjj.rateio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EventRequestDTO(
        @NotBlank(message = "O nome do evento é obrigatório")
        String name,

        String description,

        // Como ainda não temos o login com JWT a funcionar a 100%, 
        // vamos enviar o ID do utilizador no JSON para sabermos quem é o criador.
        @NotNull(message = "O ID do criador é obrigatório")
        UUID creatorId
) {}