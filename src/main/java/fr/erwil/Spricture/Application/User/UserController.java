package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> create(@RequestBody @Valid CreateUserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<GetUserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getMany());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/validate")
    public ResponseEntity<Boolean> validate(@PathVariable long id) {
        return ResponseEntity.ok(userService.validateUserAccount(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/block")
    public ResponseEntity<Boolean> block(@PathVariable long id) {
        return ResponseEntity.ok(userService.block(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/unblock")
    public ResponseEntity<Boolean> unblock(@PathVariable long id) {
        return ResponseEntity.ok(userService.unblock(id));
    }
}

