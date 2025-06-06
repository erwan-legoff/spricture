package fr.erwil.Spricture.Application.Medium.MediumStat.Dtos;

import lombok.Builder;

@Builder
public record StorageUsageResponseDto(long newEstimatedUsageStorage, boolean isEstimatedStorageLimitReached,
                                      long actualNewUsageStorage, boolean isActualStorageLimitReached,
                                      long actualRemainingStorage) {
}
