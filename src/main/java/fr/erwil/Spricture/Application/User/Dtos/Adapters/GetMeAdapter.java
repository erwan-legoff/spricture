package fr.erwil.Spricture.Application.User.Dtos.Adapters;

import fr.erwil.Spricture.Application.User.Dtos.Responses.GetMeResponseDto;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Configuration.Security.Utils.EncryptionUtils;

public class GetMeAdapter {
    public static GetMeResponseDto adapt(User user) {
        return GetMeResponseDto.builder()
                .id(user.getId())
                .pseudo(user.getPseudo())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .salt(EncryptionUtils.convertBytesToBase64String(user.getSalt()))
                .build();
    }
}
