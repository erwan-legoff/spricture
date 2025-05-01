package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Adapters.CreateUserAdapter;
import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Exceptions.User.UserCreationException;
import fr.erwil.Spricture.Tools.Mail.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserResponseDto create(CreateUserRequestDto user) {
        try {
            User userToCreate = CreateUserAdapter.getUser(user,passwordEncoder.encode(user.getRawPassword()));
            userRepository.save(userToCreate);
            return CreateUserResponseDto.builder().userCreated(true).build();
        } catch (Exception e) {
            throw new UserCreationException("Error while creating user", e);
        }

    }
}
