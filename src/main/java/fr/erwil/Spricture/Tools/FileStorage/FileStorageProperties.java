package fr.erwil.Spricture.Tools.FileStorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Here we can store all needed properties for the file.storage
 * They will be overwritten by our application.properties file
 */
@Configuration
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {

    // Default value
    private String location = "/medias";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;

    }
}
