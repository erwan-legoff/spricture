package fr.erwil.Spricture.Exceptions;

import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionsHandler {



    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(exception, request.getContextPath());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(status, exception, request.getContextPath());
        return new ResponseEntity<>(errorResponse, status);
    }

}
