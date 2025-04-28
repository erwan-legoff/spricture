package fr.erwil.Spricture.Configuration.Data.Seeding;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.Seeding.UserSeeder;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeedRunner implements CommandLineRunner {

    private final UserSeeder userSeeder;

    public DataSeedRunner(UserSeeder userSeeder) {
        this.userSeeder = userSeeder;
    }

    @Profile("dev")
    @Override
    public void run(String... args) throws Exception {

        userSeeder.seed();
        System.out.println("Users seeded successfully.");
        System.out.println("Seeding finished.");

    }
}
