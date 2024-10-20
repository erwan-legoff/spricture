package fr.erwil.Spricture.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MediumProcessingException extends RuntimeException {
    public MediumProcessingException(String message, Throwable cause){
        super(message, cause);
    }
}
