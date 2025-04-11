package fr.erwil.Spricture.Application.User.Dtos.Adapters;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.User;

public class CreateUserAdapter {

    public static User getUser(CreateUserRequestDto dto, String encryptedPassword){
        User user = new User(dto.pseudo(), dto.name(), dto.lastName(), dto.email(), encryptedPassword);
        user.setRole(dto.role());
        return user;
    }

}
