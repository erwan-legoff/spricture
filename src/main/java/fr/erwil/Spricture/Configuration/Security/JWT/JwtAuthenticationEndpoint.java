package fr.erwil.Spricture.Configuration.Security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class JwtAuthenticationEndpoint
        implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper mapper;

    public JwtAuthenticationEndpoint(ObjectMapper mapper) {
        this.mapper = mapper;                 // Spring injecte son ObjectMapper global
    }

    /* 401 – token absent ou invalide */
    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse res,
                         AuthenticationException ex) throws IOException {
        writeProblem(res, HttpStatus.UNAUTHORIZED,
                "AUTH_ERROR", "invalid_token", ex.getMessage());
    }

    /* 403 – droits insuffisants */
    @Override
    public void handle(HttpServletRequest req,
                       HttpServletResponse res,
                       AccessDeniedException ex) throws IOException {
        writeProblem(res, HttpStatus.FORBIDDEN,
                "ACCESS_DENIED", "insufficient_scope", ex.getMessage());
    }

    /* Fabrique et écrit la réponse JSON RFC 7807 */
    private void writeProblem(HttpServletResponse res,
                              HttpStatus status,
                              String errorCode,
                              String bearerError,
                              String detail) throws IOException {

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle("Authentication failure");
        pd.setProperty("errorCode", errorCode);
        pd.setProperty("timestamp", OffsetDateTime.now().toString());

        res.setStatus(status.value());
        res.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        res.setHeader("WWW-Authenticate",
                "Bearer error=\"" + bearerError +
                        "\", error_description=\"" + detail + "\"");

        mapper.writeValue(res.getOutputStream(), pd);
    }
}
