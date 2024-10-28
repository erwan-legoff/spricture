package fr.erwil.Spricture.Application.Medium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MediumController {
    private static final Logger logger = LoggerFactory.getLogger(MediumController.class);

    private final IMediumService mediumService;

    public MediumController(IMediumService mediumService){
        this.mediumService=mediumService;
    }

    @PostMapping("/medias")
    public ResponseEntity<Medium> createMedium(@RequestParam("medium") MultipartFile medium) {
        logger.info("Received request to create new medium");
        try {
            if (medium.isEmpty()) {
                logger.warn("Received an empty file. Aborting creation.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            logger.debug("File details - Name: {}, Size: {} bytes", medium.getOriginalFilename(), medium.getSize());

            Medium createdMedium = mediumService.create(medium);

            logger.info("Successfully created medium with ID: {}", createdMedium.getId());

            return new ResponseEntity<>(createdMedium, HttpStatus.CREATED);


        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating medium", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
