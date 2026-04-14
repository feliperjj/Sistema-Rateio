package feliperjj.rateio.controller;

import feliperjj.rateio.dto.EventRequestDTO;
import feliperjj.rateio.dto.EventResponseDTO;
import feliperjj.rateio.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Map<String, String>> closeEvent(@PathVariable UUID id) {
        eventService.closeEvent(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Evento fechado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO response = eventService.createEvent(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}