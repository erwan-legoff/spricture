package fr.erwil.Spricture.Exceptions.User;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Levée lorsqu’on tente de créer un utilisateur déjà existant.
 */
@Getter
public class UserPseudoAlreadyExistsException extends UserCreationException {

    private static final String CODE = "USER_PSEUDO_ALREADY_EXISTS";

    public UserPseudoAlreadyExistsException(String pseudo) {
        super(
                HttpStatus.CONFLICT,                       // 409
                String.format("[%s] User with pseudo '%s' already exists", CODE, pseudo)
        );
    }

    @Override
    public String getCode() {
        return CODE;
    }
}

