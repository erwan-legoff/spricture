package fr.erwil.Spricture.Exceptions.UuidFileStorage;

import fr.erwil.Spricture.Exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class LocalStorageException extends BaseException {
    public LocalStorageException(String message) {
        super(message);
    }

    public LocalStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
