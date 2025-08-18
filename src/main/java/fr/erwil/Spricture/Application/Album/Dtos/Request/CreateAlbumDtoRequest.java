package fr.erwil.Spricture.Application.Album.Dtos.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAlbumDtoRequest(
        @Size(max = 255)
        @NotBlank
        String name,

        @Size(max = 1000)
        String description
) {
}
