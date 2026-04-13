package feliperjj.rateio.dto;

import java.util.UUID;

// Repara que aqui NÃO incluímos a password. Esta é a versão segura que vamos devolver à aplicação mobile.
public record UserResponseDTO(
        UUID id,
        String name,
        String email
) {}