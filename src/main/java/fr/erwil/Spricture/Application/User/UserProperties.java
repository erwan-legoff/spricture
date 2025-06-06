package fr.erwil.Spricture.Application.User;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user")
@Data
public class UserProperties {
    // GigaOctet
    private long defaultQuota = 1L;
}

