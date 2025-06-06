package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserStatus;
import fr.erwil.Spricture.Configuration.Security.Utils.EncryptionUtils;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppStatUserInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IMediumStatService mediumStatService;
    private final PasswordEncoder passwordEncoder;
    private final MediumStatProperties properties;

    public AppStatUserInitializer(IUserRepository userRepository,
                                  IMediumStatService mediumStatService,
                                  PasswordEncoder passwordEncoder,
                                  MediumStatProperties properties) {
        this.userRepository = userRepository;
        this.mediumStatService = mediumStatService;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @Override
    @Transactional
    public void run(String... args) {
        userRepository.findByPseudo(properties.getAppPseudo()).orElseGet(() -> {
            User user = User.builder()
                    .pseudo(properties.getAppPseudo())
                    .email(properties.getAppEmail())
                    .password(passwordEncoder.encode(properties.getAppPassword()))
                    .salt(EncryptionUtils.generateSalt())
                    .storageQuota(properties.getAppQuota())
                    .build();
            user.setStatus(UserStatus.BLOCKED_BY_ADMIN);
            User created = userRepository.save(user);
            mediumStatService.create(created.getId());
            return created;
        });
    }
}
