package fr.erwil.Spricture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
