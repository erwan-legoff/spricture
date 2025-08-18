package fr.erwil.Spricture.Application.Album;

import fr.erwil.Spricture.Application.Album.Dtos.Request.CreateAlbumDtoRequest;
import fr.erwil.Spricture.Application.Album.Dtos.Response.CreateAlbumDtoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final IAlbumService albumService;

    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping()
    ResponseEntity<CreateAlbumDtoResponse> create(@Valid @RequestBody CreateAlbumDtoRequest dto){
        return ResponseEntity.ok(albumService.create(dto));
    }
}
