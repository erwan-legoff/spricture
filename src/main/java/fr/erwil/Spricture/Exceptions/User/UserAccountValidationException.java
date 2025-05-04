package fr.erwil.Spricture.Exceptions.User;

import org.springframework.http.HttpStatus;

public class UserAccountValidationException extends UserException {

  public UserAccountValidationException(String message) {
    super(HttpStatus.CONFLICT, message, null);
  }

  public UserAccountValidationException(String message, Throwable cause) {
    super(HttpStatus.CONFLICT, message, cause);
  }
}

