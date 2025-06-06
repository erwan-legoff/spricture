package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.Medium.MediumStat.MediumStatProperties;
import fr.erwil.Spricture.Configuration.Data.Seeding.ISeeder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements ISeeder {

    private final IUserRepository userRepository;
    private final UserFactory userFactory;
    private final MediumStatProperties properties;

    public UserSeeder(IUserRepository userRepository, UserFactory userFactory, MediumStatProperties properties) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.properties = properties;
    }

    @Override
    public void seed() {
        userRepository.deleteByPseudoNot(properties.getAppPseudo());

        userRepository.save(userFactory.getAdminUser());
        userRepository.save(userFactory.getUserUser());
        userRepository.saveAll(userFactory.getManyRandomUsers(10));
    }
}

