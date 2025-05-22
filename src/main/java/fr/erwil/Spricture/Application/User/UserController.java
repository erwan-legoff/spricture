package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetMeResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import fr.erwil.Spricture.Configuration.Security.UserDetails.UserDetailsServiceImpl;
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
    private final UserDetailsServiceImpl userDetailsService;
    public UserController(UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/send-email-validation")
    public ResponseEntity<Boolean> sendEmailValidation(@PathVariable long id) {
        return ResponseEntity.ok(userService.sendEmailValidation(id));
    }

    @GetMapping("/me")
    public ResponseEntity<GetMeResponseDto> getMe() {
        return ResponseEntity.ok(userDetailsService.getMe());
    }
}

