package fr.erwil.Spricture.Exceptions.Medium;

import org.springframework.http.HttpStatus;

public class UserStorageQuotaExceededException extends MediumProcessingException {

    public UserStorageQuotaExceededException(Long ownerId) {
        super(HttpStatus.FORBIDDEN,
                String.format("User %d has exceeded their storage quota.", ownerId));
    }
}
