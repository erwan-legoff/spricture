package fr.erwil.Spricture.Application.User.Dtos.Adapters;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.User;

public class CreateUserAdapter {

    public static User getUser(CreateUserRequestDto dto, String encryptedPassword) {
        return User.builder()
                .pseudo(dto.getPseudo())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(encryptedPassword)
                .role(dto.getRole())
                .build();
    }


}
