package fr.erwil.Spricture.Application.Medium;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MediumController {
private final IMediumService mediumService;

    public MediumController(IMediumService mediumService){
        this.mediumService=mediumService;
    }

    @PostMapping("/medias")
    public ResponseEntity<Medium> createMedium(@RequestBody MultipartFile multipartFile) throws IOException {
        Medium createdMedium = mediumService.create(multipartFile);
        return new ResponseEntity<>(createdMedium, HttpStatus.CREATED);
    }

}
