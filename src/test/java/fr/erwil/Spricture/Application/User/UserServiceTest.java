package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Seeding.UserFactory;
import fr.erwil.Spricture.Exceptions.User.UserAccountValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    static UserFactory userFactory;



    @Mock
    PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        Mockito.when(encoder.encode(Mockito.anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        userFactory = new UserFactory(encoder);
    }

    @Test
    void validateShouldChangeStatusToValidatedByAdmin(){
        Long userId = 1L;
        User user = userFactory.getRandomUser(UserStatus.EMAIL_VALIDATED);
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(userId);
        boolean result = userService.validateUserAccount(userId);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        user.setStatus(UserStatus.VALIDATED_BY_ADMIN);
        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        Mockito.verifyNoMoreInteractions(userRepository);
        assertTrue(result);

    }

    @Test
    void validateShouldThrowConflictWhenUserAccountIsAlreadyValidated(){
        Long userId = 1L;
        User user = userFactory.getRandomUser(UserStatus.VALIDATED_BY_ADMIN);
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(userId);
        assertThrows(UserAccountValidationException.class, ()->userService.validateUserAccount(userId));
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);;
        Mockito.verifyNoMoreInteractions(userRepository);
    }
}
