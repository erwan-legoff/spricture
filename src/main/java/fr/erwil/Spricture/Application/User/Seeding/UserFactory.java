package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import fr.erwil.Spricture.Application.User.UserStatus;
import fr.erwil.Spricture.Configuration.Security.Utils.EncryptionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.erwil.Spricture.Application.User.Seeding.UserSeedProperties;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class UserFactory implements IUserFactory {

    private final PasswordEncoder passwordEncoder;
    private final UserSeedProperties seedProperties;

    public UserFactory(PasswordEncoder passwordEncoder, UserSeedProperties seedProperties) {
        this.passwordEncoder = passwordEncoder;
        this.seedProperties = seedProperties;
    }

    @Override
    public User getAdminUser() {
        User admin = User.builder()
                .pseudo("UserAdmin")
                .name("Administrator")
                .lastName("LastName")
                .email(seedProperties.getAdminEmail())
                .password(passwordEncoder.encode(seedProperties.getAdminPassword()))
                .role(UserRole.ROLE_ADMIN)
                .salt(EncryptionUtils.generateSalt())
                .storageQuota(2L)
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
                .password(passwordEncoder.encode(seedProperties.getDefaultPassword()))
                .role(UserRole.ROLE_USER)
                .salt(EncryptionUtils.generateSalt())
                .storageQuota(1L)
                .build();
        user.setStatus(UserStatus.VALIDATED_BY_ADMIN);
        return user;
    }

    @Override
    public User getRandomUser() {
        return this.getRandomUser(UserStatus.VALIDATED_BY_ADMIN);
    }

    @Override
    public User getRandomUser(UserStatus status){
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        User randomUser = User.builder()
                .pseudo("user_" + uuid)
                .name("Random")
                .lastName("User")
                .email("random_" + uuid + "@example.com")
                .password(passwordEncoder.encode(seedProperties.getDefaultPassword()))
                .salt(EncryptionUtils.generateSalt())
                .storageQuota(1L)
                .build();
        randomUser.setStatus(status);
        return randomUser;
    }

    @Override
    public List<User> getManyRandomUsers(int number) {
        return IntStream.range(0, number)
                .mapToObj(i->getRandomUser())
                .toList();
    }
}
