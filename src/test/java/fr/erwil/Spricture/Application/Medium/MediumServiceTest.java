package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.Request.GetMediumDto;
import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import fr.erwil.Spricture.Tools.FileStorage.UuidFileStorageSimple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MediumServiceTest {



    @Mock
    IMediumRepository mediumRepository;

    @Mock
    UuidFileStorageSimple fileStorage;

    @InjectMocks
    MediumService mediumService;


    @Test
    void getMediumResourceShouldReturnSomething() throws IOException {
        UUID uuid = new UUID(1, 1);
        String fileName = uuid.toString();
        Path tempFile = Files.createTempFile(fileName,"");
        try {
            Files.writeString(tempFile, "test");
            byte[] expectedContent = Files.readAllBytes(tempFile);
            GetMediumDto getMediumDto = new GetMediumDto(uuid);

            Mockito.doReturn(tempFile).when(fileStorage).read(uuid);

            InputStreamResource actualResult = mediumService.getFile(getMediumDto);
            byte[] actualContent = actualResult.getInputStream().readAllBytes();

            Mockito.verify(fileStorage, Mockito.times(1)).read(uuid);
            Mockito.verifyNoMoreInteractions(fileStorage);

            assertNotNull(mediumService);
            assertNotNull(actualContent);
            assertArrayEquals(expectedContent, actualContent);
        }finally {
            Files.deleteIfExists(tempFile);
        }

    }

    @Test
    void getMediumResourceShouldThrowMediumNotFoundException() throws IOException {
        UUID uuid = new UUID(1, 1);
        GetMediumDto getMediumDto = new GetMediumDto(uuid);

        Mockito.doThrow(FileNotFoundException.class).when(fileStorage).read(uuid);

        assertThrows(MediumNotFoundException.class, () -> mediumService.getFile(getMediumDto));


    }
}