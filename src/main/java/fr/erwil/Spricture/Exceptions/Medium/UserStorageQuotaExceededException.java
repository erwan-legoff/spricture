package fr.erwil.Spricture.Exceptions.Medium;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserStorageQuotaExceededException extends MediumProcessingException {

    private final Long userId;
    private final StorageUsageResponseDto usage;

    private final long attemptedUploadSizeInBytes;

    public UserStorageQuotaExceededException(Long userId, StorageUsageResponseDto usage, long attemptedUploadSizeInBytes) {
        super(HttpStatus.FORBIDDEN, String.format(
                "User %d exceeded quota: attempted upload %d bytes, estimated usage after upload = %d Go / quota remaining = %d Go",
                userId,
                attemptedUploadSizeInBytes,
                usage.newEstimatedUsageStorage(),
                usage.actualRemainingStorage()
        ));
        this.userId = userId;
        this.usage = usage;
        this.attemptedUploadSizeInBytes = attemptedUploadSizeInBytes;
    }

}

