package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;

public interface IMediumStatService {
    StorageUsageResponseDto increaseStorageUsage(long userId, long bytesAdded);
    void decreaseStorageUsage(long userId, long bytesDeleted);
    StorageUsageResponseDto computeStorageUsage(long userId);
    StorageUsageResponseDto computeFullStorageUsage();
    void create(long userId);
}
