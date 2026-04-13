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

    public ParticipantResponseDTO addParticipant(ParticipantRequestDTO dto) {
        // 1. Verifica se a pessoa já está na viagem (usando a sua regra do UniqueConstraint!)
        if (participantRepository.existsByEventIdAndUserId(dto.eventId(), dto.userId())) {
            throw new IllegalArgumentException("Este usuário já é participante deste evento!");
        }

        // 2. Busca o Evento e o Usuário
        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // 3. Salva o Participante
        Participant participant = new Participant();
        participant.setEvent(event);
        participant.setUser(user);

        Participant savedParticipant = participantRepository.save(participant);

        // 4. Retorna os dados bonitinhos
        return new ParticipantResponseDTO(
                savedParticipant.getId(),
                event.getName(),
                user.getName()
        );
    }
}