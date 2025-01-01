package fr.erwil.Spricture.Application.Medium;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        Files.writeString(tempFile, "test");

        Mockito.doReturn(tempFile).when(mediumService).getFile(dtoWithId(uuid));

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

}
