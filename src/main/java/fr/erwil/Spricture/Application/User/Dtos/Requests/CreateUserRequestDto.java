package fr.erwil.Spricture.Application.User.Dtos.Requests;

import fr.erwil.Spricture.Application.User.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotBlank
    @Size(min = 3, max = 30)
    private String pseudo;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String rawPassword;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private UserRole role;
}