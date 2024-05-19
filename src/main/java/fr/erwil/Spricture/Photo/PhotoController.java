package fr.erwil.Spricture.Photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RequestMapping("/photos")
@RestController
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @GetMapping("{id}")
    public ResponseEntity<PhotoDto> getPhotoById(@PathVariable Long id) {
        Optional<PhotoDto> photo = photoService.getPhotoById(id);

        return photo.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PhotoDto> createPhoto(@RequestBody PhotoDto photo){
        PhotoDto savedPhoto = photoService.createPhoto(photo);
        return new ResponseEntity<>(photo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Photo>> getAllPhotos(){
        Iterable<Photo> photos = photoService.getAllPhotos();
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

}
