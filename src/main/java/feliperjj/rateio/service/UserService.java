package feliperjj.rateio.service;

import feliperjj.rateio.domain.User;
import feliperjj.rateio.dto.UserRegisterRequestDTO;
import feliperjj.rateio.dto.UserResponseDTO;
import feliperjj.rateio.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // A injeção de dependências via construtor é a melhor prática no Spring
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO register(UserRegisterRequestDTO dto) {
        // 1. Regra de Negócio: Verificar se o e-mail já existe
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Este e-mail já está registado.");
        }

        // 2. Transformar o DTO numa Entidade
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        
        // NOTA: Por agora vamos guardar a password em texto limpo para testar o fluxo.
        // No passo da Segurança, vamos substituir esta linha por um hash (BCrypt).
        user.setPasswordHash(dto.password());

        // 3. Guardar na base de dados
        User savedUser = userRepository.save(user);

        // 4. Devolver apenas os dados seguros (sem a password)
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
}