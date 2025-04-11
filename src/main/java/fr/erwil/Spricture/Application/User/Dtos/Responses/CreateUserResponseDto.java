package fr.erwil.Spricture.Application.User.Dtos.Responses;

public class CreateUserResponseDto {
    private final boolean isUserCreated;

    public CreateUserResponseDto(boolean isUserCreated) {
        this.isUserCreated = isUserCreated;
    }

    public boolean isUserCreated() {
        return isUserCreated;
    }
}
