package feliperjj.rateio.config;

import feliperjj.rateio.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Olha para o cabeçalho do pedido à procura da palavra "Authorization"
        final String authHeader = request.getHeader("Authorization");

        // 2. Se não tiver token, ou se não começar por "Bearer ", deixa o Spring Security bloquear.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai apenas o texto do token (tira os primeiros 7 caracteres: "Bearer ")
        final String token = authHeader.substring(7);

        // 4. Se o token for válido, dizemos ao Spring: "Este utilizador está autenticado!"
        if (jwtService.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String userId = jwtService.extractUserId(token);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userId, null, new ArrayList<>()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 5. Continua o fluxo normal da API
        filterChain.doFilter(request, response);
    }
}