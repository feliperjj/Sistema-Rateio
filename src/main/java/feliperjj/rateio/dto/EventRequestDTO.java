package feliperjj.rateio.dto;

import jakarta.validation.constraints.NotBlank;

public record EventRequestDTO(
        @NotBlank(message = "O nome do evento é obrigatório")
        String name,

        String description
        
        // 🚨 O creatorId FOI REMOVIDO DAQUI! 🚨
) {}