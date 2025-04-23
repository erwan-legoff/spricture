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
    private final MailService mailService;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public CreateUserResponseDto create(CreateUserRequestDto user) {
        try {
            User userToCreate = CreateUserAdapter.getUser(user,passwordEncoder.encode(user.getRawPassword()));
            // TODO : Create a email validation process
            mailService.sendSimpleMessage(userToCreate.getEmail(),"Validate your email address.", "Please click on this link to validate your email address.");
            userRepository.save(userToCreate);
            mailService.sendSimpleMessage(userToCreate.getEmail(),"Account Creation Success !", "Your account has been successfully created !");
            return CreateUserResponseDto.builder().userCreated(true).build();
        } catch (Exception e) {
            throw new UserCreationException("Error while creating user", e);
        }

    }
}
