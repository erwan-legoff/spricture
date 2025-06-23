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
		// ATTENTION : Éviter de logger le mot de passe en production, mais utile pour le debug temporaire
		// System.out.println("DEBUG: spring.datasource.password = " + env.getProperty("spring.datasource.password"));

		// 2. Vérifier si les variables d'environnement brutes sont vues
		System.out.println("DEBUG: DB_URL (raw env var) = " + env.getProperty("DB_URL"));
		System.out.println("DEBUG: DB_USER (raw env var) = " + env.getProperty("DB_USER"));
		System.out.println("DEBUG: DB_PASS (raw env var) = " + env.getProperty("DB_PASS"));

		// 3. Vérifier le profil actif
		String[] activeProfiles = env.getActiveProfiles();
		if (activeProfiles.length > 0) {
			System.out.println("DEBUG: Active Spring Profiles = " + String.join(", ", activeProfiles));
		} else {
			System.out.println("DEBUG: No active Spring profiles (defaulting)");
		}
		System.out.println("DEBUG: spring.profiles.active = " + env.getProperty("spring.profiles.active"));
		System.out.println("DEBUG: SPRING_PROFILES_ACTIVE (raw env var) = " + env.getProperty("SPRING_PROFILES_ACTIVE"));

		// 4. Vérifier le nom de l'application (pour s'assurer que d'autres variables sont bien lues)
		System.out.println("DEBUG: spring.application.name = " + env.getProperty("spring.application.name"));
		System.out.println("DEBUG: SPRING_APPLICATION_NAME (raw env var) = " + env.getProperty("SPRING_APPLICATION_NAME"));

		// 5. Vérifier le port d'écoute (si Render attend un port spécifique)
		System.out.println("DEBUG: server.port = " + env.getProperty("server.port"));
		System.out.println("DEBUG: PORT (Render specific env var) = " + env.getProperty("PORT"));

		System.out.println("--- End Spring Boot Environment Debug ---");
	}

}
