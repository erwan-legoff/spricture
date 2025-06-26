package fr.erwil.Spricture.Application.User.Seeding;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Base64;

@Data
@Configuration
@ConfigurationProperties(prefix = "user.seed")
public class UserSeedProperties {
    private String adminEmail = "admin@example.com";
    private String adminPassword = generateRandomPassword();
    private String defaultPassword = generateRandomPassword();

    private static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
