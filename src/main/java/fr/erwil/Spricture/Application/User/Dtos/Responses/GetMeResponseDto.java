package fr.erwil.Spricture.Application.User.Dtos.Responses;


import lombok.Builder;

@Builder
public record GetMeResponseDto(
        Long id,
        String pseudo,
        String name,
        String lastName,
        String email,
        String salt
) {}
