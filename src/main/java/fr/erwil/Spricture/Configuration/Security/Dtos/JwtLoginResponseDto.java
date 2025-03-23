package fr.erwil.Spricture.Configuration.Security.Dtos;

public class JwtLoginResponseDto {
    private final String accessToken;

    public JwtLoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
