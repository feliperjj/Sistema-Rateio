package feliperjj.rateio.exception;

import io.swagger.v3.oas.annotations.Hidden; // 👈 1. ADICIONE ESTE IMPORT AQUI
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Hidden // 👈 2. COLOQUE A CAPA DE INVISIBILIDADE AQUI!
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Trata erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // ... (mantenha o seu código como estava)
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 2. Trata erros de regras de negócio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // ... (mantenha o seu código como estava)
        Map<String, String> error = new HashMap<>();
        error.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 3. Trata erros genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralExceptions(Exception ex) {
        ex.printStackTrace(); // Mantemos o nosso rastreador
        Map<String, String> error = new HashMap<>();
        error.put("erro", "Ocorreu um erro interno no servidor.");
        error.put("detalhe", ex.getMessage()); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}