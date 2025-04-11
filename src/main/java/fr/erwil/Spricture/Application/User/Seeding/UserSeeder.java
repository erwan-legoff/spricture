package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.UserRole;
import fr.erwil.Spricture.Application.User.UserService;
import fr.erwil.Spricture.Configuration.Seeding.ISeeder;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import org.springframework.context.annotation.Profile;
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
    }
}

