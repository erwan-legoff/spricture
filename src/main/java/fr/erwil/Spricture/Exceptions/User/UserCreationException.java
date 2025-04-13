package fr.erwil.Spricture.Exceptions.User;

import org.springframework.http.HttpStatus;

public class UserCreationException extends UserException {

  public UserCreationException( String message, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
  }
}
