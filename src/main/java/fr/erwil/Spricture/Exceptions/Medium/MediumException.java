package fr.erwil.Spricture.Exceptions.Medium;

import fr.erwil.Spricture.Exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class MediumException extends BaseException {
    public MediumException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus,message, cause);
    }

    public MediumException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
