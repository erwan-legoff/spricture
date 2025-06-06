package fr.erwil.Spricture.Exceptions.Medium;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MediumProcessingException extends MediumException {

    public MediumProcessingException(String message){
        super(HttpStatus.INTERNAL_SERVER_ERROR,message);
    }
    public MediumProcessingException(String message, Throwable cause){
        super(HttpStatus.INTERNAL_SERVER_ERROR,message, cause);
    }
    public MediumProcessingException(HttpStatus httpStatus,String message, Throwable cause){
        super(httpStatus,message, cause);
    }
    public MediumProcessingException(HttpStatus httpStatus,String message){
        super(httpStatus,message);
    }
}
