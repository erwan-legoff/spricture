package fr.erwil.Spricture.Tools.FileStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UuidFileStorageSimpleTest {

    private FileStorageProperties properties;
    private UuidFileStorageSimple fileStorage;
    private Path tempDirectory;

    @BeforeEach
    void setUp() throws IOException {
        // Créer une instance réelle de FileStorageProperties
        properties = new FileStorageProperties();

        // Utiliser un répertoire temporaire pour les tests
        tempDirectory = Files.createTempDirectory("test-media");
        properties.setLocation(tempDirectory.toString());

        // Initialiser UuidFileStorageSimple avec les propriétés configurées
        fileStorage = new UuidFileStorageSimple(properties);
    }

    @AfterEach
    void tearDown() throws IOException {
        fileStorage.deleteAll();
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    void saveShouldSavesAFile() throws IOException {
        assertNotNull(fileStorage);
        UUID uuid = UUID.randomUUID();

        Path expectedPath = tempDirectory.resolve(uuid.toString());
        String expectedContent = "content";
        MultipartFile file = new MockMultipartFile
                ("name",
                        "fileName",
                        "contentType",
                        expectedContent.getBytes());
        assertFalse(Files.exists(expectedPath));
        assertDoesNotThrow(() -> fileStorage.save(file, uuid));

        assertTrue(Files.exists(expectedPath));
        assertEquals(expectedContent, Files.readString(expectedPath));


    }


    @Test
    void readShouldReturnPathIfFileExists() throws IOException {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Path expectedPath = tempDirectory.resolve(uuid.toString());
        String expectedContent = "Hello, World!";

        Files.writeString(expectedPath, expectedContent);


        Path actualPath = fileStorage.read(uuid);

        assertNotNull(actualPath);
        assertTrue(Files.exists(actualPath));
        assertEquals(expectedContent, Files.readString(actualPath));
    }

    @Test
    void readShouldThrowFileNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        assertThrows(FileNotFoundException.class, ()->fileStorage.read(uuid));
            }
}
