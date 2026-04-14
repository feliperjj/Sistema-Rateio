package feliperjj.rateio.controller;

import feliperjj.rateio.domain.User;
import feliperjj.rateio.repository.UserRepository;
import feliperjj.rateio.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Um pequeno DTO interno só para receber o e-mail e a palavra-passe
    public record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Procurar o utilizador pelo e-mail
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou palavra-passe incorretos."));

        // 2. A Mágica: O passwordEncoder compara a palavra-passe que o utilizador digitou com o Hash da base de dados
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "E-mail ou palavra-passe incorretos."));
        }

        // 3. Se tudo estiver certo, gerar e devolver a "pulseira VIP" (Token JWT)
        String token = jwtService.generateToken(user);
        
        return ResponseEntity.ok(Map.of("token", token));
    }
}