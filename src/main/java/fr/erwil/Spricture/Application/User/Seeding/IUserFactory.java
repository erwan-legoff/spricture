package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;

import java.util.List;

public interface IUserFactory {
    User getRandomUser();
    User getUserUser();
    User getAdminUser();
    List<User> getManyRandomUsers(int number);
}
