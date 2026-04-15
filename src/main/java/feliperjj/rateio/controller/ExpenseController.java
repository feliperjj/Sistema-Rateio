package feliperjj.rateio.controller;

import feliperjj.rateio.dto.ExpenseRequestDTO;
import feliperjj.rateio.dto.ExpenseResponseDTO;
import feliperjj.rateio.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(
            @Valid @RequestBody ExpenseRequestDTO requestDTO, 
            Authentication authentication) {
        
        // O "Pulo do Gato": Extrai o ID de quem está a pagar diretamente do Token!
        UUID payerId = UUID.fromString((String) authentication.getPrincipal());
        
        ExpenseResponseDTO response = expenseService.createExpense(requestDTO, payerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}