package fr.erwil.Spricture.Application.Medium.MediumStat;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.Seeding.UserFactory;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserStatus;
import fr.erwil.Spricture.Configuration.Security.Utils.EncryptionUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class AppStatUserInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppStatUserInitializer.class);

    private final IUserRepository userRepository;
    private final IMediumStatService mediumStatService;
    private final PasswordEncoder passwordEncoder;
    private final MediumStatProperties properties;
    private final UserFactory userFactory;

    public AppStatUserInitializer(IUserRepository userRepository,
                                  IMediumStatService mediumStatService,
                                  PasswordEncoder passwordEncoder,
                                  MediumStatProperties properties,
                                  UserFactory userFactory) {
        this.userRepository = userRepository;
        this.mediumStatService = mediumStatService;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
        this.userFactory = userFactory;
    }

    @Override
    @Transactional
    public void run(String... args) {
        createAppStatUserIfAbsent();
        createAdminUserIfAbsent();
    }

    private void createAppStatUserIfAbsent() {
        userRepository.findByPseudo(properties.getAppPseudo()).ifPresentOrElse(
                existing -> logger.info("AppStat user [{}] already exists with id={}",
                        existing.getPseudo(), existing.getId()),
                () -> {
                    User user = User.builder()
                            .pseudo(properties.getAppPseudo())
                            .email(properties.getAppEmail())
                            .password(passwordEncoder.encode(properties.getAppPassword()))
                            .salt(EncryptionUtils.generateSalt())
                            .storageQuota(properties.getAppQuota())
                            .build();
                    user.setStatus(UserStatus.BLOCKED_BY_ADMIN);
                    User created = userRepository.saveAndFlush(user);
                    logger.info("Created AppStat user [{}] with id={}", created.getPseudo(), created.getId());
                    mediumStatService.create(created.getId());
                }
        );
    }

    private void createAdminUserIfAbsent() {
        User adminUser = userFactory.getAdminUser();

        if (!userRepository.existsByEmail(adminUser.getEmail())) {
            userRepository.save(adminUser);
            logger.info("Admin user [{}] inserted successfully.", adminUser.getEmail());
        } else {
            logger.info("Admin user [{}] already exists. Insertion skipped.", adminUser.getEmail());
        }
    }
}