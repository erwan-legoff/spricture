package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.Medium.MediumStat.MediumStatProperties;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Configuration.Data.Seeding.ISeeder;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class UserSeeder implements ISeeder {

    private static final Logger logger = LoggerFactory.getLogger(UserSeeder.class);

    private final IUserRepository userRepository;
    private final UserFactory userFactory;
    private final MediumStatProperties properties;

    public UserSeeder(IUserRepository userRepository, UserFactory userFactory, MediumStatProperties properties) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.properties = properties;
    }

    @Override
    @Transactional
    public void seed() {
        userRepository.deleteByPseudoNot(properties.getAppPseudo());

        User adminUser = userFactory.getAdminUser();
        if (!userRepository.existsByEmail(adminUser.getEmail())) {
            userRepository.save(adminUser);
            logger.info("Utilisateur admin inséré avec succès.");
        } else {
            logger.info("L'utilisateur admin existe déjà, insertion ignorée.");
        }

        User userUser = userFactory.getUserUser();
        if (!userRepository.existsByEmail(userUser.getEmail())) {
            userRepository.save(userUser);
            logger.info("Utilisateur user inséré avec succès.");
        } else {
            logger.info("L'utilisateur user existe déjà, insertion ignorée.");
        }

        List<User> randomUsers = userFactory.getManyRandomUsers(10);
        for (User randomUser : randomUsers) {
            if (!userRepository.existsByEmail(randomUser.getEmail())) {
                userRepository.save(randomUser);
                logger.info("Utilisateur aléatoire inséré avec succès : {}", randomUser.getEmail());
            } else {
                logger.info("L'utilisateur avec email {} existe déjà, insertion ignorée.", randomUser.getEmail());
            }
        }
    }
}