package fr.erwil.Spricture.Configuration.Data.Seeding;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeedRunner implements CommandLineRunner {

    private final IUserRepository userRepository;

    public DataSeedRunner(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        if(!Arrays.asList(args).contains("seed")){
            System.out.println("No 'seed' argument. Skipping seeding...");
            return;
        }*/

        if(userRepository.count() > 0){
            System.out.println("Users already exist. Skipping seeding.");
        }

        //TODO : to put in a dedicated sseder
        User admin = new User();
        admin.setPseudo("adminPseudo");
        admin.setName("Admin");
        admin.setLastName("Test");
        admin.setEmail("admin@company.com");
        admin.setPassword("secret123"); // à encoder
        admin.setRole(UserRole.ADMIN);

        User user = new User();
        user.setPseudo("johnDoe");
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john@domain.com");
        user.setPassword("password"); // à encoder
        user.setRole(UserRole.USER);

        userRepository.save(admin);
        userRepository.save(user);

        System.out.println("Users seeded successfully.");

    }
}
