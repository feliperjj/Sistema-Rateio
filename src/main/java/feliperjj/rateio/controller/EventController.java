package feliperjj.rateio.controller;

import feliperjj.rateio.dto.EventRequestDTO;
import feliperjj.rateio.dto.EventResponseDTO;
import feliperjj.rateio.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(
            @Valid @RequestBody EventRequestDTO requestDTO, 
            Authentication authentication) {
        
        // O "Pulo do Gato": Extrai o ID do utilizador logado diretamente do Token JWT!
        UUID creatorId = UUID.fromString((String) authentication.getPrincipal());
        
        EventResponseDTO response = eventService.createEvent(requestDTO, creatorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Rota para fechar o evento e calcular o rateio
    @PatchMapping("/{id}/close")
    public ResponseEntity<String> closeEvent(@PathVariable UUID id) {
        eventService.closeEvent(id);
        return ResponseEntity.ok("Evento fechado com sucesso. Cálculos de rateio gerados.");
    }
}