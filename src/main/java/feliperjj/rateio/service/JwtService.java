package feliperjj.rateio.service;

import feliperjj.rateio.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    
    // A chave secreta (num projeto real, isto fica escondido no application.properties)
    // TEM DE TER PELO MENOS 32 CARACTERES PARA SER SEGURA!
    private static final String SECRET_STRING = "MinhaChaveSecretaSuperSeguraParaORateioAPI2026";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString()) // O token "pertence" ao ID deste utilizador
                .claim("name", user.getName())    // Podemos guardar dados extras dentro do token
                .claim("email", user.getEmail())
                .issuedAt(new Date()) // Data de criação
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expira em 24 horas
                .signWith(key) // Assina com a nossa chave secreta
                .compact();
    }

    // 1. Verifica se o token é verdadeiro e se ainda está dentro da validade
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false; // Se der erro (falso ou expirado), o segurança barra a entrada.
        }
    }

    // 2. Tira o ID do utilizador de dentro do token
    public String extractUserId(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
}