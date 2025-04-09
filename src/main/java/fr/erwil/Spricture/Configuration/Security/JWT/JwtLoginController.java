package fr.erwil.Spricture.Configuration.Security.JWT;

import fr.erwil.Spricture.Configuration.Security.Dtos.JwtLoginResponseDto;
import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JwtLoginController {
    private final IAuthService authService;

    public JwtLoginController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtLoginResponseDto> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtLoginResponseDto responseDto = new JwtLoginResponseDto(token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
