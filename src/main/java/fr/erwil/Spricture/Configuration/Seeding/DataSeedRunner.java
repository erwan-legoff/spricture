package fr.erwil.Spricture.Configuration.Seeding;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeedRunner implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeedRunner(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        if(userRepository.count() > 0){
            System.out.println("Users already exist. Skipping seeding.");
        }

        //TODO : to put in a dedicated sseder
        User admin = new User();
        admin.setPseudo("adminPseudo");
        admin.setName("Admin");
        admin.setLastName("Test");
        admin.setEmail("admin@company.com");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setRole(UserRole.ROLE_ADMIN);

        User user = new User();
        user.setPseudo("johnDoe");
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john@domain.com");
        user.setPassword(passwordEncoder.encode("password")); // à encoder
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(admin);
        userRepository.save(user);

        System.out.println("Users seeded successfully.");

    }
}
