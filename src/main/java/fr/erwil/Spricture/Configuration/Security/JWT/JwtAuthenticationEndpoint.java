package fr.erwil.Spricture.Configuration.Security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Component
public class JwtAuthenticationEndpoint implements AuthenticationEntryPoint {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException, ServletException {


        HttpStatus status       = HttpStatus.UNAUTHORIZED;
        String     bearerError  = "invalid_token";
        String     errorCode    = "AUTH_ERROR";

        switch (exception) {
            case DisabledException _ -> {
                status      = HttpStatus.FORBIDDEN;   // 403
                bearerError = "account_disabled";
                errorCode   = "ACCOUNT_DISABLED";
            }
            case AccountExpiredException _ -> {
                bearerError = "account_expired";
                errorCode   = "ACCOUNT_EXPIRED";
            }
            case CredentialsExpiredException _ -> {
                bearerError = "credentials_expired";
                errorCode   = "CREDENTIALS_EXPIRED";
            }
            case LockedException _ -> {
                bearerError = "account_locked";
                errorCode   = "ACCOUNT_LOCKED";
            }
            default -> {}
        }



        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle("Authentication failure");
        problem.setProperty("errorCode", errorCode);
        problem.setProperty("timestamp", OffsetDateTime.now());


        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("WWW-Authenticate",
                "Bearer error=\"" + bearerError + "\", error_description=\"" + exception.getMessage() + "\"");

        mapper.writeValue(response.getOutputStream(), problem);
    }
}
