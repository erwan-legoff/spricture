package fr.erwil.Spricture.Exceptions.User;

import org.springframework.http.HttpStatus;

public class UserUnblockException extends UserException {

    public UserUnblockException(String message) {
        super(HttpStatus.CONFLICT, message, null);
    }

    public UserUnblockException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, cause);
    }
}
