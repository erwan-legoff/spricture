package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Configuration.Data.Seeding.ISeeder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements ISeeder {

    private final IUserRepository userRepository;
    private final UserFactory userFactory;

    public UserSeeder(IUserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public void seed() {
        userRepository.deleteAll();

        userRepository.save(userFactory.getAdminUser());
        userRepository.save(userFactory.getUserUser());
        userRepository.saveAll(userFactory.getManyRandomUsers(10));
    }
}

