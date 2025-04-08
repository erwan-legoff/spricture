package fr.erwil.Spricture.Configuration.Security.JWT;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtProperties {
    private String secretKey;

    private long expirationMilliseconds = 60*60*1000;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationMilliseconds() {
        return expirationMilliseconds;
    }

    public void setExpirationMilliseconds(long expirationMilliseconds) {
        this.expirationMilliseconds = expirationMilliseconds;
    }
}
