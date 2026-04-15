package feliperjj.rateio.service;

import feliperjj.rateio.domain.Event;
import feliperjj.rateio.domain.User;
import feliperjj.rateio.dto.EventRequestDTO;
import feliperjj.rateio.dto.EventResponseDTO;
import feliperjj.rateio.repository.EventRepository;
import feliperjj.rateio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // Método novo para fechar o evento!
    public void closeEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        if (event.isClosed()) {
            throw new IllegalArgumentException("Este evento já foi fechado anteriormente.");
        }

        event.setClosed(true); // O método setClosed agora é garantido
        eventRepository.save(event);
    }

    public EventResponseDTO createEvent(EventRequestDTO dto, UUID creatorId) {
        
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        Event event = new Event();
        event.setName(dto.name());
        event.setDescription(dto.description());
        event.setCreator(creator);
        
        event.setClosed(false); // 👈 O evento nasce aberto

        Event savedEvent = eventRepository.save(event);

        return new EventResponseDTO(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getDescription(),
                creator.getId(),
                creator.getName()
        );
    }
} // 👈 APENAS UMA CHAVE AQUI!