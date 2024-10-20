package fr.erwil.Spricture.Application.Medium;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMediumRepository extends JpaRepository<Medium, UUID> {
}
