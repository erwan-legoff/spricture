package fr.erwil.Spricture.Configuration.Security.JWT;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtProperties {

    private String secretKey;

    private long loginExpirationMilliseconds = 1000 * 60 * 60;

    private long validateExpirationMilliseconds = 1000 * 60 * 10;

}
