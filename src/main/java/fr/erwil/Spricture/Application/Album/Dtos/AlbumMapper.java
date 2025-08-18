package fr.erwil.Spricture.Application.Album.Dtos;

import fr.erwil.Spricture.Application.Album.Album;
import fr.erwil.Spricture.Application.Album.Dtos.Request.CreateAlbumDtoRequest;
import fr.erwil.Spricture.Application.Album.Dtos.Response.CreateAlbumDtoResponse;

public class AlbumMapper {
    public static Album toEntity(CreateAlbumDtoRequest dto){
        return new Album(dto.title(), dto.description());
    }

    public static CreateAlbumDtoResponse toCreateResponse(Album entity){
        return new CreateAlbumDtoResponse(entity.getId());
    }
}
