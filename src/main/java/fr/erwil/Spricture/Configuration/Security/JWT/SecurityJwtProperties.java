package fr.erwil.Spricture.Configuration.Security.JWT;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtProperties {
    @Getter
    @Setter
    private String secretKey;

    @Setter
    @Getter
    private long loginExpirationMilliseconds = 1000 * 60 * 60;
    @Setter
    @Getter
    private long validateExpirationMilliseconds = 1000 * 60 * 10;

}
