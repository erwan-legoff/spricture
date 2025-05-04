package fr.erwil.Spricture.Application.User.Dtos.Adapters;

import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import fr.erwil.Spricture.Application.User.User;

public class GetUserResponseAdapter {
    public static GetUserResponseDto adapt(User user) {
        Long mediaCount = user.getMedia() != null ? (long) user.getMedia().size() : 0L;
        return GetUserResponseDto.builder()
                .pseudo(user.getPseudo())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .deletedAt(user.getDeletedAt())
                .mediaCount(mediaCount)
                .build();
    }
}
