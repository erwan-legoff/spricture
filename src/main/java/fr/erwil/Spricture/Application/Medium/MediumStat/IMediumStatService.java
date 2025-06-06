package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.Medium.MediumStat.Dtos.StorageUsageResponseDto;

public interface IMediumStatService {
    StorageUsageResponseDto increaseStorageUsage(long userId, long sizeAdded);
    StorageUsageResponseDto decreaseStorageUsage(long userId, long sizeDeleted);
    StorageUsageResponseDto computeStorageUsage(long userId);
    StorageUsageResponseDto computeFullStorageUsage();
}
