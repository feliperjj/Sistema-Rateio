package feliperjj.rateio.repository;

import feliperjj.rateio.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // O Spring Boot cria a query de SQL automaticamente só pelo nome do método!
    Optional<User> findByEmail(String email);
}