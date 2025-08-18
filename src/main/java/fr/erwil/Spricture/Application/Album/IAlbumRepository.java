package fr.erwil.Spricture.Application.Album;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findOneByTitle(String title);
    boolean existsByTitle(String title);
}
