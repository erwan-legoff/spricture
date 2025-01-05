package fr.erwil.Spricture.Exceptions;

import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionsHandler {


    private static final Logger log = LogManager.getLogger(GlobalExceptionsHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("BaseException at {}: {}", path, exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse(exception, path);
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }





    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("MaxUploadSizeExceededException at {}: {}", path, exception.getMessage(), exception);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.getReasonPhrase(),
                "File size exceeds the maximum allowed limit", path);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        log.error("BaseException at {}: {}", path, exception.getMessage(), exception);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(status, exception, path);
        return new ResponseEntity<>(errorResponse, status);
    }

}
