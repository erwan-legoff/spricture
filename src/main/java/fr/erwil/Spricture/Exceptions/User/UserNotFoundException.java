package fr.erwil.Spricture.Exceptions.User;

import fr.erwil.Spricture.Exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {

  public UserNotFoundException(String message, Throwable cause) {
    super(HttpStatus.NOT_FOUND, message, cause);
  }
}
