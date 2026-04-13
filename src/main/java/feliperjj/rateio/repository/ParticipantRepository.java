package feliperjj.rateio.repository;

import feliperjj.rateio.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    // Para listar todos os participantes de um evento específico
    List<Participant> findByEventId(UUID eventId);
    
    // Para verificar se um utilizador já está num evento
    Optional<Participant> findByEventIdAndUserId(UUID eventId, UUID userId);
}