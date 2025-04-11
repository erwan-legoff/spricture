package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.Dtos.Requests.CreateUserRequestDto;
import fr.erwil.Spricture.Application.User.UserRole;
import fr.erwil.Spricture.Application.User.UserService;
import fr.erwil.Spricture.Configuration.Seeding.ISeeder;

public class UserSeeder implements ISeeder {

    private final UserService userService;

    public UserSeeder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void seed() {
        CreateUserRequestDto userAdmin =
                new CreateUserRequestDto(
                                "UserAdmin",
                                "admin@company.com",
                                "admin",
                                "Administrator",
                                "LastName",
                                UserRole.ROLE_ADMIN);


        userService.create(userAdmin);

        CreateUserRequestDto user =
                new CreateUserRequestDto(
                        "User",
                        "user@company.com",
                        "user",
                        "User",
                        "LastName",
                        UserRole.ROLE_USER);

        userService.create(user);
    }
}
