package feliperjj.rateio.service;

import feliperjj.rateio.domain.User;
import feliperjj.rateio.dto.UserRegisterRequestDTO;
import feliperjj.rateio.dto.UserResponseDTO;
import feliperjj.rateio.repository.UserRepository;
import org.springframework.stereotype.Service;
// 👇 NOVO IMPORT AQUI 👇
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 👈 1. Declarar o Encoder

    // 👈 2. Injetar o Encoder no construtor
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Este e-mail já está registado.");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        
        // 👈 3. A MÁGICA ACONTECE AQUI: A palavra-passe é codificada antes de ser guardada
        user.setPasswordHash(passwordEncoder.encode(dto.password()));

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
}