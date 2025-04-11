package fr.erwil.Spricture.Application.User.Dtos.Requests;

import fr.erwil.Spricture.Application.User.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDto(
        @NotBlank @Size(min = 3, max = 30) String pseudo,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String rawPassword,
        @NotBlank String name,
        @NotBlank String lastName,
        UserRole role
) {}
