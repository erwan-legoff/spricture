package fr.erwil.Spricture.Exceptions.Medium.MediumStat;

import fr.erwil.Spricture.Exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class MediumStatNotFoundException extends BaseException {

    public MediumStatNotFoundException(Long userId) {
        super(HttpStatus.NOT_FOUND, "No MediumStat found for user ID: " + userId);
    }
}

