package fr.erwil.Spricture.Configuration.Security.JWT;

import org.springframework.security.core.Authentication;

public interface IJwtTokenProvider {
    String generateToken(Authentication authentication);
    String extractUserName(String token);
    boolean validateToken(String token);
}
