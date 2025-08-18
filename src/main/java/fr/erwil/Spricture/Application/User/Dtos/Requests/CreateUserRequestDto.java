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
    @Size(min = 6, max = 30)
    private String rawPassword;

    @Size(min = 1, max = 60)
    @NotBlank
    private String name;

    @Size(min = 1, max = 100)
    @NotBlank
    private String lastName;

}