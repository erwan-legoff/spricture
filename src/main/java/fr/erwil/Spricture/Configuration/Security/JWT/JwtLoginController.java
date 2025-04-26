package fr.erwil.Spricture.Configuration.Security.JWT;

import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ResponseCookie cookie = buildJwtCookie(token, 60 * 60 * 24);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Set-Cookie", cookie.toString())
                .build();
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verify(@RequestParam String token) {
        boolean isValidated = authService.verify(token);


        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/send-verification-email")
    public ResponseEntity<Void> verificationEmail(@RequestParam String email) {
        authService.sendVerificationEmail(email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        String deletedToken = "";
        ResponseCookie cookie = buildJwtCookie(deletedToken, 0);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Set-Cookie", cookie.toString())
                .build();
    }

    private ResponseCookie buildJwtCookie(String token, int maxAge) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(isProd)
                .sameSite(this.sameSite)
                .path("/")
                .maxAge(maxAge)
                .build();
    }




}

