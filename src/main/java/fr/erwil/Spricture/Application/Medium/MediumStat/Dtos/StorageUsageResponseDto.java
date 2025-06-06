package fr.erwil.Spricture.Application.Medium.MediumStat.Dtos;

import lombok.Builder;

@Builder
public record StorageUsageResponseDto(float newEstimatedUsageStorage, boolean isEstimatedStorageLimitReached,
                                      float actualNewUsageStorage, boolean isActualStorageLimitReached,
                                      float actualRemainingStorage) {
}
