package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.User.User;

public interface IMediumStatService {
    float increaseStorageUsage(User user, float sizeAdded);

    float decreaseStorageUsage(User user, float sizeDeleted);

    float computeStorageUsage(User user);

    float computeFullStorageUsage();
}
