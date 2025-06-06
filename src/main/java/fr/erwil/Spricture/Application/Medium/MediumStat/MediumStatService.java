package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;
import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Exceptions.Medium.MediumStat.MediumStatNotFoundException;
import fr.erwil.Spricture.Exceptions.User.UserNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Optional;

public class MediumStatService implements IMediumStatService{
    private final IMediumStatRepository mediumStatRepository;
    private final IUserRepository userRepository;

    public MediumStatService(IMediumStatRepository mediumStatRepository, IUserRepository userRepository) {
        this.mediumStatRepository = mediumStatRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public StorageUsageResponseDto increaseStorageUsage(long userId, long sizeAdded) {
        MediumStat mediumStat = mediumStatRepository
                .findByUser_Id(userId)
                .orElseThrow(() -> new MediumStatNotFoundException(userId));

        final long originalStorageUsage = mediumStat.getStorageUsage();
        final long newEstimatedStorage =  originalStorageUsage + sizeAdded;
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found while checking for state."));

        final long quota = user.getStorageQuota();

        if(newEstimatedStorage >= quota){
            long actualRemainingStorage = quota - originalStorageUsage;
            return StorageUsageResponseDto.builder()
                    .newEstimatedUsageStorage(newEstimatedStorage)
                    .actualNewUsageStorage(originalStorageUsage)
                    .actualRemainingStorage(actualRemainingStorage)
                    .isEstimatedStorageLimitReached(true)
                    .isActualStorageLimitReached(actualRemainingStorage <= 0)
                    .build();
        }
        long actualRemainingStorage = quota - newEstimatedStorage;

        mediumStat.setStorageUsage(newEstimatedStorage);

        return StorageUsageResponseDto.builder()
                .newEstimatedUsageStorage(newEstimatedStorage)
                .actualNewUsageStorage(newEstimatedStorage)
                .isActualStorageLimitReached(false)
                .isEstimatedStorageLimitReached(false)
                .actualRemainingStorage(actualRemainingStorage).build();
    }

    @Override
    public StorageUsageResponseDto decreaseStorageUsage(long userId, long sizeDeleted) {
        return null;
    }

    @Override
    public StorageUsageResponseDto computeStorageUsage(long userId) {
        return null;
    }

    @Override
    public StorageUsageResponseDto computeFullStorageUsage() {
        return null;
    }
}
