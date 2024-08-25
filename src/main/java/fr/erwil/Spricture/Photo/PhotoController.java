package fr.erwil.Spricture.Photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@RequestMapping("/photos")
@RestController
public class PhotoController {
    private static final Logger log = LoggerFactory.getLogger(PhotoController.class);
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
        log.debug(photo.toString());
        PhotoDto savedPhoto = photoService.createPhoto(photo);
        return new ResponseEntity<>(photo, HttpStatus.CREATED);
    }

    @PostMapping("/dumb")
    public ResponseEntity<String> uploadDumbPhoto(@RequestBody MultipartFile photo){
        photoService.createPhotoDumb(photo);

        return new ResponseEntity<>(photo.getName(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<PhotoDto>> getAllPhotos(){
        return new ResponseEntity<>(photoService.getAllPhotos(), HttpStatus.OK);
    }

//    @GetMapping('/dumb')
//    public ResponseEntity<Iterable<MultipartFile>> getAllPhotosDumb(){
//        return new ResponseEntity<>(photoService.getAllPhotos(), HttpStatus.OK);
//    }

}
