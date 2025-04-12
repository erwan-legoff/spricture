package fr.erwil.Spricture.Application.User.Dtos.Responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserResponseDto {
    boolean userCreated;
}

