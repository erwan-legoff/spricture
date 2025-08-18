package fr.erwil.Spricture.Exceptions.Album;

import fr.erwil.Spricture.Exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AlbumException extends BaseException {
    public AlbumException(String message) {
        super(message);
    }

    public AlbumException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public AlbumException(HttpStatus httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }

    public AlbumException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return "ALBUM_ERROR";
    }
}
