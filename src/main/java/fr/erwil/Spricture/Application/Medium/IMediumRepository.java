package fr.erwil.Spricture.Application.Medium;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IMediumRepository extends JpaRepository<Medium, UUID> {
    List<Medium> findByOwnerIdAndDeletedAtIsNull(Long ownerId);
    List<UUID> findIdByOwnerIdAndDeletedAtIsNull(Long ownerId);

}
