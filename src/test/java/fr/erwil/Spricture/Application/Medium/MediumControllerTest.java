package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MediumControllerTest {

    @MockBean
    IMediumService mediumService; // The service is needed for the controller

    @Autowired
    private MockMvc mockMvc;

    private static GetMediumDto dtoWithId(UUID uuid) {
        return Mockito.argThat(dto -> dto.getId().equals(uuid));
    }



    @Test
    void getMediumReturnsTheFileItReceivedAndSucceed() throws Exception {

        UUID uuid = new UUID(1,1);
        String fileName = uuid.toString();
        Path tempFile = Files.createTempFile(fileName,"");
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(tempFile));
        Files.writeString(tempFile, "test");

        Mockito.doReturn(resource).when(mediumService).getFile(dtoWithId(uuid));

        MvcResult result = mockMvc.perform(get("/medium").param("id",fileName))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        Mockito.verify(mediumService, Mockito.times(1)).getFile(dtoWithId(uuid));
        Assertions.assertNotNull(responseBody);


        String fileContentExpected = Files.readString(tempFile);

        assertEquals(fileContentExpected, responseBody);


        Files.deleteIfExists(tempFile);
    }

    @Test
    void getMediumReturn404WhenTheFileDoesNotExist() throws Exception {

        UUID uuid = new UUID(1,1);
        String fileName = uuid.toString();

        Mockito.doThrow(new MediumNotFoundException("a message", new Throwable())).when(mediumService).getFile(dtoWithId(uuid));

        mockMvc.perform(get("/medium").param("id",fileName))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound());

    }

}
