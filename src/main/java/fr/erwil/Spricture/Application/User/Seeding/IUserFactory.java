package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Application.User.UserRole;
import fr.erwil.Spricture.Application.User.UserStatus;

import java.util.List;

public interface IUserFactory {
    User getRandomUser();
    User getRandomUser(UserStatus status);
    User getUserUser();
    User getAdminUser();
    List<User> getManyRandomUsers(int number);
}
