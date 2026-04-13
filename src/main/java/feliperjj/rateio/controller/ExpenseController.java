package feliperjj.rateio.controller;

import feliperjj.rateio.dto.ExpenseRequestDTO;
import feliperjj.rateio.dto.ExpenseResponseDTO;
import feliperjj.rateio.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO requestDTO) {
        ExpenseResponseDTO response = expenseService.createExpense(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}