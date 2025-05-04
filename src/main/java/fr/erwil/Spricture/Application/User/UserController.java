package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    ResponseEntity<List<GetUserResponseDto>> getUsers(){
        var responseDto = userService.getMany();
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);

    }

}
