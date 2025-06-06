package fr.erwil.Spricture.Application.Medium.MediumStat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "medium.stat")
public class MediumStatProperties {
    private String appPseudo = "APP_STAT";
    private String appEmail = "app_stat@spricture.local";
    private String appPassword = "app_stat";
    // quota in GO
    private long appQuota = 10L;
    // percentage of quota allowed before blocking new uploads
    private int quotaThresholdPercent = 90;
}
