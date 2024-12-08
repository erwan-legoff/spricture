package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import fr.erwil.Spricture.Exceptions.MediumNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // say to Spring that I use Mockito
public class MediumControllerTest {
    @InjectMocks
    MediumController mediumController;

    @Mock
    IMediumService mediumService; // The service is needed for the controller

    private static GetMediumDto dtoWithId(UUID uuid) {
        return Mockito.argThat(dto -> dto.getId().equals(uuid));
    }



    @Test
    void getMediumReturnsTheFileItReceivedAndSucceed() throws IOException {



        UUID uuid = new UUID(1,1);
        String fileName = uuid.toString();
        Path tempFile = Files.createTempFile(fileName,"");
        Files.writeString(tempFile, "test");

        Mockito.doReturn(tempFile).when(mediumService).getMediumResource(dtoWithId(uuid));
        ResponseEntity<Resource> result = mediumController.getMedium(fileName);

        Mockito.verify(mediumService, Mockito.times(1)).getMediumResource(dtoWithId(uuid));
        Assertions.assertNotNull(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        String fileContentExpected = Files.readString(tempFile);
        String actualFileContent = new String(result.getBody().getInputStream().readAllBytes());
        assertEquals(fileContentExpected, actualFileContent);


        Files.deleteIfExists(tempFile);
    }

}
