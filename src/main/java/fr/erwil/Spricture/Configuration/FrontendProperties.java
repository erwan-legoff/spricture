package fr.erwil.Spricture.Configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "frontend")
public class FrontendProperties {
    String host = "http://localhost:3000/";
    String verifyAccount = "account-validated";
}
