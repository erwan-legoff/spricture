package fr.erwil.Spricture.Application.Medium.Dtos;

import java.util.UUID;

public class GetMediumDto {
    private final UUID id;

    public GetMediumDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
