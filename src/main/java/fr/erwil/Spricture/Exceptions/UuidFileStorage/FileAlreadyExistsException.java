package fr.erwil.Spricture.Exceptions.UuidFileStorage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileAlreadyExistsException extends RuntimeException {
    public FileAlreadyExistsException(String message){
        super(message);
    }
}
