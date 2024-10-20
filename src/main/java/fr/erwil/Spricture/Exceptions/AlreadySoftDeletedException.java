package fr.erwil.Spricture.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadySoftDeletedException extends RuntimeException{

    public AlreadySoftDeletedException(String message){
        super(message);
    }

}
