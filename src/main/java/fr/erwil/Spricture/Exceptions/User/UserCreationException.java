package fr.erwil.Spricture.Exceptions.User;

import org.springframework.http.HttpStatus;

public class UserCreationException extends UserException {

  public UserCreationException(HttpStatus status, String message) {
    super(status, message, null);
  }

  public UserCreationException(HttpStatus status, String message, Throwable cause) {
    super(status, message, cause);
  }

  public UserCreationException(String message, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
  }
  @Override
  public String getCode() {
    return "USER_CREATION_ERROR";
  }
}
