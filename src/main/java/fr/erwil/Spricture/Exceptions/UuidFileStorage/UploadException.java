package fr.erwil.Spricture.Exceptions.UuidFileStorage;

import fr.erwil.Spricture.Exceptions.BaseException;

public class UploadException extends BaseException {
    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
