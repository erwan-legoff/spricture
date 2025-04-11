package fr.erwil.Spricture.Application.User.Dtos.Requests;

import fr.erwil.Spricture.Application.User.UserRole;

public class CreateUserRequestDto {
    private final String pseudo, email, rawPassword, name, lastName;
    private final UserRole role;

    public CreateUserRequestDto(String pseudo, String email, String rawPassword, String name, String lastName, UserRole role) {
        this.pseudo = pseudo;
        this.email = email;
        this.rawPassword = rawPassword;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
