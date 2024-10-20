package fr.erwil.Spricture.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionsHandler {



    @ExceptionHandler(MediumProcessingException.class)
    public ResponseEntity<String> handleIOException(MediumProcessingException processingException) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors du traitement du fichier : " + processingException.getMessage());
    }


    // Tu peux ajouter d'autres gestionnaires pour différentes exceptions ici

}
