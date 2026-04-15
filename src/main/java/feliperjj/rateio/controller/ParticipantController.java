package feliperjj.rateio.controller;

import feliperjj.rateio.dto.ParticipantRequestDTO;
import feliperjj.rateio.dto.ParticipantResponseDTO;
import feliperjj.rateio.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDTO> joinEvent(
            @Valid @RequestBody ParticipantRequestDTO requestDTO, 
            Authentication authentication) {
        
        // A Mágica: Extrai o ID do utilizador logado diretamente do Token JWT!
        UUID userId = UUID.fromString((String) authentication.getPrincipal());
        
        ParticipantResponseDTO response = participantService.createParticipant(requestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}