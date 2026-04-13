package feliperjj.rateio.controller;

import feliperjj.rateio.dto.ParticipantRequestDTO;
import feliperjj.rateio.dto.ParticipantResponseDTO;
import feliperjj.rateio.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDTO> addParticipant(@Valid @RequestBody ParticipantRequestDTO requestDTO) {
        ParticipantResponseDTO response = participantService.addParticipant(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}