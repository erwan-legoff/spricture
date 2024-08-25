package fr.erwil.Spricture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//exclude = {DataSourceAutoConfiguration.class}
@RestController
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
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // Utiliser "/**" pour appliquer CORS à toutes les routes
						.allowedOriginPatterns("http://localhost:*")  // Autoriser tous les ports de localhost
						.allowedMethods("POST", "GET", "OPTIONS", "DELETE");
			}
		};
	}

}
