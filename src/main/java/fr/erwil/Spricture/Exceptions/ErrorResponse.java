package fr.erwil.Spricture.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(Instant.now(), status, error, message, path);
    }

    public ErrorResponse(BaseException exception, String path){
        this(exception.getHttpStatus().value(),exception.getHttpStatus().name(), exception.getMessage(), path);
    }

    public  ErrorResponse(HttpStatus httpStatus, Exception exception, String path){
        this(httpStatus.value(), httpStatus.name(), exception.getMessage(), path);
    }
}
