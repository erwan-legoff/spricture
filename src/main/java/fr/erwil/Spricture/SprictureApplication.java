package fr.erwil.Spricture;

import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//exclude = {DataSourceAutoConfiguration.class}
@RestController
@EnableJpaAuditing
@SpringBootApplication()
public class SprictureApplication {

	@RequestMapping("/")
	String home(){
		return "Welcome to my first Spring Project!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SprictureApplication.class, args);
	}

	@Autowired
	private Environment env;
	@Bean
	@Profile("dev")
	public CommandLineRunner applicationInitialization(IUuidFileStorage fileStorage){
		return (args) -> {
			fileStorage.deleteAll();
		} ;
	}

	@PostConstruct
	public void logApplicationProperties() {
		System.out.println("--- Spring Boot Environment Debug ---");

		// 1. Vérifier les propriétés de la DataSource
		System.out.println("DEBUG: spring.datasource.url = " + env.getProperty("spring.datasource.url"));
		System.out.println("DEBUG: spring.datasource.username = " + env.getProperty("spring.datasource.username"));

		// 2. Vérifier si les variables d'environnement brutes sont vues
		System.out.println("DEBUG: DB_URL (raw env var) = " + env.getProperty("DB_URL"));
		System.out.println("DEBUG: DB_USER (raw env var) = " + env.getProperty("DB_USER"));

		// 3. Vérifier le profil actif
		String[] activeProfiles = env.getActiveProfiles();
		if (activeProfiles.length > 0) {
			System.out.println("DEBUG: Active Spring Profiles = " + String.join(", ", activeProfiles));
		} else {
			System.out.println("DEBUG: No active Spring profiles (defaulting)");
		}
		System.out.println("DEBUG: spring.profiles.active = " + env.getProperty("spring.profiles.active"));
		System.out.println("DEBUG: SPRING_PROFILES_ACTIVE (raw env var) = " + env.getProperty("SPRING_PROFILES_ACTIVE"));

		System.out.println("--- End Spring Boot Environment Debug ---");
	}

}
