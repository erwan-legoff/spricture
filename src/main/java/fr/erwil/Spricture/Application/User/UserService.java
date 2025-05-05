package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Adapters.CreateUserAdapter;
import fr.erwil.Spricture.Application.User.Dtos.Adapters.GetManyUsersResponseAdapter;
import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import fr.erwil.Spricture.Configuration.FrontendProperties;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import fr.erwil.Spricture.Exceptions.User.*;
import fr.erwil.Spricture.Tools.Mail.MailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final FrontendProperties frontendProperties;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService, FrontendProperties frontendProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.frontendProperties = frontendProperties;
    }

    @Override
    public CreateUserResponseDto create(CreateUserRequestDto user) {
        try {
            User userToCreate = CreateUserAdapter.getUser(user,passwordEncoder.encode(user.getRawPassword()));
            userToCreate.setStatus(UserStatus.CREATED);
            userRepository.save(userToCreate);
            return CreateUserResponseDto.builder().userCreated(true).build();
        } catch (Exception e) {
            throw new UserCreationException("Error while creating user", e);
        }
    }

    @Override
    public List<GetUserResponseDto> getMany() {
        return GetManyUsersResponseAdapter.adapt(this.userRepository.findAll());
    }

    @Override
    public boolean validateUserAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        UserStatus status = user.getStatus();

        if (status.isBlocked()) {
            throw new UserAccountValidationException(
                    "Cannot validate a blocked account. Please unblock it first. Current status: " + status
            );
        }

        if (!status.emailHasBeenValidated()) {
            throw new UserAccountValidationException(
                    "Cannot validate an account whose email is not validated. Current status: " + status
            );
        }

        if (status.canLogin()) {
            throw new UserAccountValidationException(
                    "User is already validated and can log in. Current status: " + status
            );
        }

        user.setStatus(UserStatus.VALIDATED_BY_ADMIN);
        userRepository.save(user);

        return true;
    }
    @Override
    public boolean block(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        UserStatus status = user.getStatus();

        if (status.isBlocked()) {
            throw new UserBlockageException(
                    "User is already blocked. Current status: " + status
            );
        }

        user.setStatus(UserStatus.BLOCKED_BY_ADMIN);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean unblock(Long userId) {
        User user = getUser(userId);

        UserStatus status = user.getStatus();

        if (!status.isBlocked()) {
            throw new UserUnblockException(
                    "User is already unblocked. Current status: " + status
            );
        }

        user.setStatus(UserStatus.CREATED);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean sendEmailValidation(Long userId) {
        User user = getUser(userId);
        UserStatus status = user.getStatus();

        if (status.isBlocked()) {
            throw new UserAccountValidationException(
                    "Cannot send validation e-mail to a blocked user. Current status: " + status
            );
        }
        if (status.emailHasBeenValidated()) {
            throw new UserAccountValidationException(
                    "E-mail already validated. Current status: " + status
            );
        }
        String verifyAccountLink = this.getEmailToUriFunction().apply(user.getEmail()).toString();
        mailService.sendSimpleMessage(user.getEmail(),"Click on the link to send a mail validation.",verifyAccountLink);
        return true;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    /**
     * Builds the public URL (front-end) that the user will click to validate the token.
     */
    private Function<String, URI> getEmailToUriFunction() {
        return email -> {
            String base = frontendProperties.getHost();
            if (!base.endsWith("/")) base += "/"; //clean up the host

            String path = frontendProperties.getSendMailVerification();
            if (path.startsWith("/")) path = path.substring(1); //cleanup the path

            String encoded = URLEncoder.encode(email, StandardCharsets.UTF_8);
            return URI.create(base + path + "?email=" + email);
        };
    }

}
