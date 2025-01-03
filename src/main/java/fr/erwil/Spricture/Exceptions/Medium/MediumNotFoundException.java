package fr.erwil.Spricture.Exceptions.Medium;

import org.springframework.http.HttpStatus;

public class MediumNotFoundException extends MediumException{
    public MediumNotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND,message, cause);
    }
}
