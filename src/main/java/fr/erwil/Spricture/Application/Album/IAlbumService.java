package fr.erwil.Spricture.Application.Album;

import fr.erwil.Spricture.Application.Album.Dtos.Request.*;
import fr.erwil.Spricture.Application.Album.Dtos.Response.*;

public interface IAlbumService {

    CreateAlbumDtoResponse create(CreateAlbumDtoRequest dto);
    AddMediumDtoResponse addMedium(AddMediumDtoRequest dto);
    AddMediaDtoResponse addMedia(AddMediaDtoRequest dto);
    DetachMediumDtoResponse detachMedium(DetachMediumDtoRequest dto);
    EditAlbumDtoResponse edit(EditAlbumDtoRequest dto);
    DeleteAlbumDtoResponse delete(DeleteAlbumDtoRequest dto);

}
