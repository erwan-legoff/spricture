package fr.erwil.Spricture.Application.Medium.Dtos;

import java.util.UUID;

public class FullDeleteMediumDto {
    private final UUID id;

    public FullDeleteMediumDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }


}
