package fr.erwil.Spricture.Configuration.Security.Dtos;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LoginDto {

    private final String username;
    private final String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
