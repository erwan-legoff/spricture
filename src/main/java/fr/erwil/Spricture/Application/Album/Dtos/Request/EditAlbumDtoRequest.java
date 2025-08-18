package fr.erwil.Spricture.Application.Album.Dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditAlbumDtoRequest(
        @Size(max = 255)
        @NotBlank
        String name,

        @Size(max = 1000)
        String description
) {
}
