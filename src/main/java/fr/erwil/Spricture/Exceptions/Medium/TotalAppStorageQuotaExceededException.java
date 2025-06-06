package fr.erwil.Spricture.Exceptions.Medium;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TotalAppStorageQuotaExceededException extends MediumProcessingException {

    private final StorageUsageResponseDto usage;
    private final long attemptedUploadSizeInBytes;

    public TotalAppStorageQuotaExceededException(StorageUsageResponseDto usage, long attemptedUploadSizeInBytes) {
        super(HttpStatus.FORBIDDEN, String.format(
                "Application storage exceeded: attempted upload %d bytes, estimated usage after upload = %d Go / quota remaining = %d Go",
                attemptedUploadSizeInBytes,
                usage.newEstimatedUsageStorage(),
                usage.actualRemainingStorage()
        ));
        this.usage = usage;
        this.attemptedUploadSizeInBytes = attemptedUploadSizeInBytes;
    }
}
