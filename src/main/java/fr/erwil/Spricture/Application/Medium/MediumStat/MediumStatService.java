package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;
import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.Medium.MediumStat.MediumStatProperties;
import fr.erwil.Spricture.Exceptions.Medium.TotalAppStorageQuotaExceededException;
import fr.erwil.Spricture.Exceptions.User.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MediumStatService implements IMediumStatService {

    private final IMediumStatRepository mediumStatRepository;
    private final IUserRepository userRepository;
    private final MediumStatProperties mediumStatProperties;

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

        StorageUsageResponseDto appUsage = increaseFullStorage(bytesAdded);
        if (appUsage.isEstimatedStorageLimitReached()) {
            throw new TotalAppStorageQuotaExceededException(appUsage, bytesAdded);
        }

        mediumStat.setStorageUsage(estimatedUsage);
        return buildStorageUsageResponse(estimatedUsage, quotaInBytes);
    }


    @Transactional
    @Override
    public void decreaseStorageUsage(long userId, long bytesDeleted) {
        MediumStat mediumStat = getMediumStat(userId);
        mediumStat.setStorageUsage(mediumStat.getStorageUsage() - bytesDeleted);
        decreaseFullStorage(bytesDeleted);
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
        User appUser = userRepository.findByPseudo(mediumStatProperties.getAppPseudo())
                .orElseThrow(() -> new UserNotFoundException("App stat user not found"));

        long usage = mediumStatRepository
                .sumStorageUsageExceptUserId(appUser.getId())
                .orElse(0L);

        MediumStat mediumStat = getMediumStat(appUser.getId());
        mediumStat.setStorageUsage(usage);

        long quota = goToBytes(appUser.getStorageQuota());
        return buildStorageUsageResponse(usage, quota);
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

    private StorageUsageResponseDto increaseFullStorage(long bytesAdded) {
        User appUser = userRepository.findByPseudo(mediumStatProperties.getAppPseudo())
                .orElseThrow(() -> new UserNotFoundException("App stat user not found"));

        MediumStat mediumStat = getMediumStat(appUser.getId());
        long originalUsage = mediumStat.getStorageUsage();
        long estimatedUsage = originalUsage + bytesAdded;

        long quotaInBytes = goToBytes(appUser.getStorageQuota());
        long threshold = (quotaInBytes * mediumStatProperties.getQuotaThresholdPercent()) / 100;

        if (estimatedUsage >= threshold) {
            return buildEstimationStorageResponse(originalUsage, estimatedUsage, quotaInBytes);
        }

        mediumStat.setStorageUsage(estimatedUsage);
        return buildStorageUsageResponse(estimatedUsage, quotaInBytes);
    }

    private void decreaseFullStorage(long bytesDeleted) {
        User appUser = userRepository.findByPseudo(mediumStatProperties.getAppPseudo())
                .orElseThrow(() -> new UserNotFoundException("App stat user not found"));
        MediumStat mediumStat = getMediumStat(appUser.getId());
        mediumStat.setStorageUsage(Math.max(mediumStat.getStorageUsage() - bytesDeleted, 0));
    }

}
