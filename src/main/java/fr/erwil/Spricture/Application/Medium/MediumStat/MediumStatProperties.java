package fr.erwil.Spricture.Application.Medium.MediumStat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "medium.stat")
public class MediumStatProperties {
    private String appPseudo = "APP_STAT";
}
