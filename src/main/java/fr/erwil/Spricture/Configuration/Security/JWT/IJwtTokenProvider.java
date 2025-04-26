package fr.erwil.Spricture.Configuration.Security.JWT;

import org.springframework.security.core.Authentication;

public interface IJwtTokenProvider {

    String generateLoginToken(String username);

    String generateVerifyToken(String username);

    String extractUserName(String token);
    boolean validateToken(String token);
}
