package feliperjj.rateio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // 👇 AGORA COM EVENTOS E DESPESAS LIBERADOS 👇
                .requestMatchers(
        "/api/users/register", 
        "/api/events", "/api/events/**",
        "/api/expenses", "/api/expenses/**",
        "/api/participants", "/api/participants/**" // 
            ).permitAll() 
                
                .anyRequest().authenticated()
            );

        return http.build();
    }
}