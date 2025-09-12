package fr.erwil.Spricture.Configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "admin")
@Data
public class AdminProperties {
    String mail;
}
