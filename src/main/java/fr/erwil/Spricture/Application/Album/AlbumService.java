package fr.erwil.Spricture.Application.Album;

import fr.erwil.Spricture.Application.Album.Dtos.AlbumMapper;
import fr.erwil.Spricture.Application.Album.Dtos.Request.*;
import fr.erwil.Spricture.Application.Album.Dtos.Response.*;
import fr.erwil.Spricture.Exceptions.Album.AlbumAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AlbumService implements IAlbumService {
    private final IAlbumRepository repository;

    public AlbumService(IAlbumRepository repository) {
        this.repository = repository;
    }


    @Override
    public CreateAlbumDtoResponse create(CreateAlbumDtoRequest dto) {
        if(repository.existsByTitle(dto.title())){
            throw new AlbumAlreadyExistsException("Album with title '" + dto.title() + "' already exists");
        }
        Album albumToCreate = AlbumMapper.toEntity(dto);
        try {
         Album albumCreated = repository.save(albumToCreate);
         return AlbumMapper.toCreateResponse(albumCreated);
        } catch (DataIntegrityViolationException e) {
            throw new AlbumAlreadyExistsException("Album with title '" + dto.title() + "' already exists");
        }
    }

    @Override
    public AddMediumDtoResponse addMedium(AddMediumDtoRequest dto) {
        return null;
    }

    @Override
    public AddMediaDtoResponse addMedia(AddMediaDtoRequest dto) {
        return null;
    }

    @Override
    public DetachMediumDtoResponse detachMedium(DetachMediumDtoRequest dto) {
        return null;
    }

    @Override
    public EditAlbumDtoResponse edit(EditAlbumDtoRequest dto) {
        return null;
    }

    @Override
    public DeleteAlbumDtoResponse delete(DeleteAlbumDtoRequest dto) {
        return null;
    }
}
