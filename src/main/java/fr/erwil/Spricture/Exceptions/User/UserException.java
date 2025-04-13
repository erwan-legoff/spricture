package fr.erwil.Spricture.Exceptions.User;

import fr.erwil.Spricture.Exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserException extends BaseException {
    public UserException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }
}
