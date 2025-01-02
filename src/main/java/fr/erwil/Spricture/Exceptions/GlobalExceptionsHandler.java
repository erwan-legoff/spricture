package fr.erwil.Spricture.Exceptions;

import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {



    @ExceptionHandler(MediumProcessingException.class)
    public ResponseEntity<String> handleIOException(MediumProcessingException processingException) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors du traitement du fichier : " + processingException.getMessage());
    }

    @ExceptionHandler(MediumNotFoundException.class)
    public ResponseEntity<String> handleMediumNotFoundException(MediumNotFoundException processingException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("MEDIUM NOT FOUND : " + processingException.getMessage());
    }

}
