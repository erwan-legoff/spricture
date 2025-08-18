package fr.erwil.Spricture.Exceptions.Album;

import org.springframework.http.HttpStatus;

public class AlbumAlreadyExistsException extends AlbumException{
    public AlbumAlreadyExistsException(String message) {
        super(message);
    }

    public AlbumAlreadyExistsException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public AlbumAlreadyExistsException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }

    public AlbumAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return "ALBUM_ALREADY_EXISTS_EXCEPTION";
    }
}
