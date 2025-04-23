package fr.erwil.Spricture.Configuration.Security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class JwtLoginControllerStandaloneTest {

    @Mock
    IAuthService authService;

    MockMvc mvc;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockEnvironment env = new MockEnvironment().withProperty("spring.profiles.active", "dev");
        JwtLoginController controller = new JwtLoginController(authService, env);

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void login_returns_jwt_cookie() throws Exception {
        String token = "token";
        when(authService.login(any())).thenReturn(token);
        LoginDto dto = new LoginDto("user", "pwd");

        // Act + Assert
        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(cookie().value("jwt", token))
                .andExpect(cookie().httpOnly("jwt", true))
                .andExpect(cookie().secure("jwt", false))
                .andExpect(cookie().maxAge("jwt", 60*60*24));
    }
}
