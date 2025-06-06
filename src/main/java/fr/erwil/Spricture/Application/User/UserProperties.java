package fr.erwil.Spricture.Application.User;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user")
@Data
public class UserProperties {
    // GigaOctet
    private long defaultQuota = 1L;
}

