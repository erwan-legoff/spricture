package fr.erwil.Spricture.Configuration.Security.JWT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "security.jwt.secret-key=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=",
        "security.jwt.login-expiration-milliseconds=1111",
        "security.jwt.validate-expiration-milliseconds=2222"
})
class SecurityJwtPropertiesTest {

    @Autowired
    SecurityJwtProperties properties;

    @Test
    void propertiesAreBound() {
        assertEquals(1111, properties.getLoginExpirationMilliseconds());
        assertEquals(2222, properties.getValidateExpirationMilliseconds());
    }
}
