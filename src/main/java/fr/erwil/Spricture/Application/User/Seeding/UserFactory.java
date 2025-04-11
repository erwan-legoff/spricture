package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

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
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "user_" + uuid,
                "Random",
                "User",
                "random_" + uuid + "@example.com",
                passwordEncoder.encode("default"),
                UserRole.ROLE_USER
        );
    }

    @Override
    public List<User> getManyRandomUsers(int number) {
        return IntStream.range(0, number)
                .mapToObj(i->getRandomUser())
                .toList();
    }
}
