package feliperjj.rateio.controller;

import feliperjj.rateio.dto.UserRegisterRequestDTO;
import feliperjj.rateio.dto.UserResponseDTO;
import feliperjj.rateio.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // O @PostMapping indica que esta rota responde a pedidos HTTP POST
    // O @Valid diz ao Spring para verificar as regras (ex: @NotBlank, @Email) que colocaste no DTO
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegisterRequestDTO requestDTO) {
        
        UserResponseDTO response = userService.register(requestDTO);
        
        // Devolve o status 201 (Created) e o utilizador criado no corpo da resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}