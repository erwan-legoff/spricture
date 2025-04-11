package fr.erwil.Spricture.Application.User.Seeding;

import fr.erwil.Spricture.Application.User.User;

public interface IUserFactory {
    User getRandomUser();
    User getUserUser();
    User getAdminUser();
}
