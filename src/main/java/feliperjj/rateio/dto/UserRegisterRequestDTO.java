package feliperjj.rateio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// O record cria automaticamente o construtor e os getters por debaixo dos panos
public record UserRegisterRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A password é obrigatória")
        @Size(min = 6, message = "A password deve ter pelo menos 6 caracteres")
        String password
) {}