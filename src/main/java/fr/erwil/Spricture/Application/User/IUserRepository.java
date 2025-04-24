package fr.erwil.Spricture.Application.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPseudo(String pseudo);
    Optional<User> findByPseudoAndIsValidatedTrue(String pseudo);
}
