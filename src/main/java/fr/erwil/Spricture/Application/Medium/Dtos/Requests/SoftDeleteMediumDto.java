package fr.erwil.Spricture.Application.Medium.Dtos.Requests;

import java.util.UUID;

public class SoftDeleteMediumDto {
    private final UUID id;

    public SoftDeleteMediumDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
