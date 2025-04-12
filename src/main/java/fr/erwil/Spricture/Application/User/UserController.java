package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    ResponseEntity<CreateUserResponseDto> createUser(@RequestBody @Valid CreateUserRequestDto requestDto){
        CreateUserResponseDto responseDto = userService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);

    }
}
