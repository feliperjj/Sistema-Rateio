package feliperjj.rateio.service;

import feliperjj.rateio.domain.Event;
import feliperjj.rateio.domain.Participant;
import feliperjj.rateio.domain.User;
import feliperjj.rateio.dto.ParticipantRequestDTO;
import feliperjj.rateio.dto.ParticipantResponseDTO;
import feliperjj.rateio.repository.EventRepository;
import feliperjj.rateio.repository.ParticipantRepository;
import feliperjj.rateio.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.UUID; 
@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public ParticipantService(ParticipantRepository participantRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

   // Adicionamos o UUID userId como parâmetro
    public ParticipantResponseDTO createParticipant(ParticipantRequestDTO dto, UUID userId) {
        
        // Agora usamos o userId seguro, extraído do token
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        Participant participant = new Participant();
        participant.setEvent(event);
        participant.setUser(user);

        Participant savedParticipant = participantRepository.save(participant);

        return new ParticipantResponseDTO(
                savedParticipant.getId(),
                event.getName(),
                user.getName()
        );
    }
}