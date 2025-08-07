package fr.erwil.Spricture.Exceptions;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        String code // Code applicatif stable et toujours présent
) {

    // Constructeur principal
    public ErrorResponse(int status, String error, String message, String path, String code) {
        this(Instant.now(), status, error, message, path, code);
    }

    // Pour les exceptions métier (héritent de BaseException avec getCode())
    public ErrorResponse(BaseException exception, String path) {
        this(
                exception.getHttpStatus().value(),
                exception.getHttpStatus().name(),
                exception.getMessage(),
                path,
                exception.getCode()
        );
    }

    // Pour les exceptions non métier : on force un code par défaut
    public ErrorResponse(HttpStatus httpStatus, Exception exception, String path) {
        this(
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                path,
                "UNEXPECTED_ERROR"
        );
    }
}
