package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import fr.erwil.Spricture.Application.User.UserStatus;
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
        User admin = User.builder()
                .pseudo("UserAdmin")
                .name("Administrator")
                .lastName("LastName")
                .email("admin@company.com")
                .password(passwordEncoder.encode("admin"))
                .role(UserRole.ROLE_ADMIN)
                .build();
        admin.setStatus(UserStatus.VALIDATED_BY_ADMIN);
        return admin;
    }

    @Override
    public User getUserUser() {
        User user = User.builder()
                .pseudo("User")
                .name("User")
                .lastName("LastName")
                .email("user@company.com")
                .password(passwordEncoder.encode("user"))
                .role(UserRole.ROLE_USER)
                .build();
        user.setStatus(UserStatus.VALIDATED_BY_ADMIN);
        return user;
    }

    @Override
    public User getRandomUser() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        User randomUser = User.builder()
                .pseudo("user_" + uuid)
                .name("Random")
                .lastName("User")
                .email("random_" + uuid + "@example.com")
                .password(passwordEncoder.encode("default"))
                .build();
        randomUser.setStatus(UserStatus.VALIDATED_BY_ADMIN);
        return randomUser;
    }

    @Override
    public List<User> getManyRandomUsers(int number) {
        return IntStream.range(0, number)
                .mapToObj(i->getRandomUser())
                .toList();
    }
}
