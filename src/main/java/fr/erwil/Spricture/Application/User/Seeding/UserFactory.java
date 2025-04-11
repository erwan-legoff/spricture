package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFactory implements IUserFactory {

    private final PasswordEncoder passwordEncoder;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getAdminUser() {
        return new User(
                "UserAdmin",
                "Administrator",
                "LastName",
                "admin@company.com",
                passwordEncoder.encode("admin"),
                UserRole.ROLE_ADMIN
        );
    }

    @Override
    public User getUserUser() {
        return new User(
                "User",
                "User",
                "LastName",
                "user@company.com",
                passwordEncoder.encode("user"),
                UserRole.ROLE_USER
        );
    }

    @Override
    public User getRandomUser() {
        return new User(
                "user" + UUID.randomUUID(),
                "Random",
                "User",
                "random" + UUID.randomUUID() + "@dev.local",
                passwordEncoder.encode("default"),
                UserRole.ROLE_USER
        );
    }
}
