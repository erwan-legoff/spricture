package fr.erwil.Spricture.Application.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPseudo(String pseudo);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPseudo(String pseudo);
    void deleteByPseudoNot(String pseudo);

}
