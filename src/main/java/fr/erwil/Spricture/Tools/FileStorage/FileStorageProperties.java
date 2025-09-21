package fr.erwil.Spricture.Tools.FileStorage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Here we can store all needed properties for the file.storage
 * They will be overwritten by our application.properties file
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {

    // Default value
    private String location = "/medias";
    private String baseUrl = "http://localhost:8080/files";

}
