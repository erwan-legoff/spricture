package fr.erwil.Spricture.Configuration.Security.Dtos;

import lombok.Getter;

@Getter
public class JwtLoginResponseDto {
    private final String accessToken;

    public JwtLoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
