package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;
import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Exceptions.User.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MediumStatService implements IMediumStatService {

    private final IMediumStatRepository mediumStatRepository;
    private final IUserRepository userRepository;

    @Transactional
    @Override
    public StorageUsageResponseDto increaseStorageUsage(long userId, long bytesAdded) {
        MediumStat mediumStat = getMediumStat(userId);

        final long originalUsage = mediumStat.getStorageUsage();
        final long estimatedUsage = originalUsage + bytesAdded;

        User user = getUser(userId);
        final long quotaInBytes = goToBytes(user.getStorageQuota());

        if (estimatedUsage >= quotaInBytes) {
            return buildEstimationStorageResponse(originalUsage, estimatedUsage, quotaInBytes);
        }

        mediumStat.setStorageUsage(estimatedUsage);
        return buildStorageUsageResponse(estimatedUsage, quotaInBytes);
    }


    @Transactional
    @Override
    public void decreaseStorageUsage(long userId, long bytesDeleted) {
        MediumStat mediumStat = getMediumStat(userId);
        mediumStat.setStorageUsage(mediumStat.getStorageUsage() - bytesDeleted);
    }

    @Transactional
    @Override
    public StorageUsageResponseDto computeStorageUsage(long userId) {
        long usage = mediumStatRepository.sumStorageUsageByUserId(userId).orElse(0L);
        MediumStat mediumStat = getMediumStat(userId);
        mediumStat.setStorageUsage(usage);

        long quota = goToBytes(getUser(userId).getStorageQuota());
        return buildStorageUsageResponse(usage, quota);
    }

    @Override
    public StorageUsageResponseDto computeFullStorageUsage() {
        return null;
    }

    @Transactional
    @Override
    public MediumStat create(long userId) {
        MediumStat mediumStat = new MediumStat();
        mediumStat.setUser(getUser(userId));
        mediumStatRepository.save(mediumStat);
        this.computeStorageUsage(userId);
        return mediumStat;
    }

    private MediumStat getMediumStat(long userId) {
        return mediumStatRepository
                .findByUser_Id(userId)
                .orElseGet(() -> create(userId));
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    private StorageUsageResponseDto buildStorageUsageResponse(long usageInBytes, long quotaInBytes) {
        long remainingInBytes = quotaInBytes - usageInBytes;

        return StorageUsageResponseDto.builder()
                .actualNewUsageStorage(bytesToGo(usageInBytes))
                .newEstimatedUsageStorage(bytesToGo(usageInBytes))
                .actualRemainingStorage(bytesToGo(remainingInBytes))
                .isActualStorageLimitReached(remainingInBytes <= 0)
                .isEstimatedStorageLimitReached(remainingInBytes <= 0)
                .build();
    }

    private StorageUsageResponseDto buildEstimationStorageResponse(long originalBytesUsage, long estimatedBytesUsage, long quotaInBytes) {
        long remainingIfNotSavedInBytes = quotaInBytes - originalBytesUsage;

        return StorageUsageResponseDto.builder()
                .actualNewUsageStorage(bytesToGo(originalBytesUsage))
                .newEstimatedUsageStorage(bytesToGo(estimatedBytesUsage))
                .actualRemainingStorage(bytesToGo(remainingIfNotSavedInBytes))
                .isActualStorageLimitReached(remainingIfNotSavedInBytes <= 0)
                .isEstimatedStorageLimitReached(true)
                .build();
    }

    private long bytesToGo(long bytes) {
        return bytes / 1_000_000_000;
    }

    private long goToBytes(long go) {
        return go * 1_000_000_000;
    }

}
