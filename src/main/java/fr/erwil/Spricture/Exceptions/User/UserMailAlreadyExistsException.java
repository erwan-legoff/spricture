package fr.erwil.Spricture.Exceptions.User;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Levée lorsqu’on tente de créer un utilisateur déjà existant.
 */
@Getter
public class UserMailAlreadyExistsException extends UserCreationException {

    private static final String CODE = "USER_MAIL_ALREADY_EXISTS";

    public UserMailAlreadyExistsException(String email) {
        super(
                HttpStatus.CONFLICT,                       // 409
                String.format("[%s] User with email '%s' already exists", CODE, email)
        );
    }

    @Override
    public String getCode() {
        return CODE;
    }
}

