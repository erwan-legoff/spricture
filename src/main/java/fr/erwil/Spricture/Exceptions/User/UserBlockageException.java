package fr.erwil.Spricture.Exceptions.User;

import org.springframework.http.HttpStatus;

public class UserBlockageException extends UserException {

    public UserBlockageException(String message) {
        super(HttpStatus.CONFLICT, message, null);
    }

    public UserBlockageException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, cause);
    }
}

