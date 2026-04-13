package feliperjj.rateio.controller;

import feliperjj.rateio.dto.EventRequestDTO;
import feliperjj.rateio.dto.EventResponseDTO;
import feliperjj.rateio.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO response = eventService.createEvent(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}