package fr.erwil.Spricture.Application.User;

import fr.erwil.Spricture.Application.User.Dtos.Adapters.CreateUserAdapter;
import fr.erwil.Spricture.Application.User.Dtos.Adapters.GetManyUsersResponseAdapter;
import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.CreateUserResponseDto;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetUserResponseDto;
import fr.erwil.Spricture.Exceptions.User.UserAccountValidationException;
import fr.erwil.Spricture.Exceptions.User.UserCreationException;
import fr.erwil.Spricture.Exceptions.User.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


}
