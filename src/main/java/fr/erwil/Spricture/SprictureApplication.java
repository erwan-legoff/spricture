package fr.erwil.Spricture;

import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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


	@Bean
	@Profile("dev")
	public CommandLineRunner applicationInitialization(IUuidFileStorage fileStorage){
		return (args) -> {
			fileStorage.deleteAll();
		} ;
	}

}
