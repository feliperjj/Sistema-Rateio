package feliperjj.rateio.repository;

import feliperjj.rateio.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    // Este arquivo fica vazio por enquanto, só precisamos que ele exista!
}