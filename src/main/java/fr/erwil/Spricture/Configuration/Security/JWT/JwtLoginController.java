package fr.erwil.Spricture.Configuration.Security.JWT;

import fr.erwil.Spricture.Configuration.Security.Dtos.JwtLoginResponseDto;
import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
public class JwtLoginController {
    private final IAuthService authService;

    private final boolean isProd;
    private final String sameSite;
    public JwtLoginController(IAuthService authService, Environment environment) {
        this.authService = authService;
        this.isProd = !Arrays.asList(environment.getActiveProfiles()).contains("dev");
        this.sameSite = this.isProd ? "Strict" : "Lax";
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(isProd)
                .sameSite(this.sameSite)
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Set-Cookie", cookie.toString())
                .build();
    }


}

