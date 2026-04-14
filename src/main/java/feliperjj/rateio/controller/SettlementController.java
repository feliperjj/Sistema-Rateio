package feliperjj.rateio.controller;

import feliperjj.rateio.dto.SettlementResponseDTO;
import feliperjj.rateio.service.SettlementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events/{eventId}/settlement")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    // ANTES: public ResponseEntity<List<SettlementResponseDTO>> getSettlement(@PathVariable UUID eventId)
    // DEPOIS 👇 (Coloque o "eventId" entre aspas)
    @GetMapping
    public ResponseEntity<List<SettlementResponseDTO>> getSettlement(@PathVariable("eventId") UUID eventId) {
        List<SettlementResponseDTO> response = settlementService.calculateSettlement(eventId);
        return ResponseEntity.ok(response);
    }
}