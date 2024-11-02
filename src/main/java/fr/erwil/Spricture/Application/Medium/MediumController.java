package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MediumController {
    private static final Logger logger = LoggerFactory.getLogger(MediumController.class);

    private final IMediumService mediumService;

    public MediumController(IMediumService mediumService){
        this.mediumService=mediumService;
    }

    @PostMapping("/media")
    public ResponseEntity<Medium> createMedium(@RequestParam("medium") @NotNull  MultipartFile medium) {
        logger.info("Received request to create new medium");
        try {

            logger.debug("File details - Name: {}, Size: {} bytes", medium.getOriginalFilename(), medium.getSize());

            Medium createdMedium = mediumService.create(medium);

            logger.info("Successfully created medium with ID: {}", createdMedium.getId());

            return new ResponseEntity<>(createdMedium, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating medium", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medium")
    public ResponseEntity<Resource> getMedium(@RequestParam("id") @NotNull @NotEmpty String id){
        logger.info("Received request to get a medium");
        try {
            GetMediumDto getMediumDto = new GetMediumDto(UUID.fromString(id));
            Path mediumFile =  mediumService.getFile(getMediumDto);

            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            return ResponseEntity.ok()
                    .contentLength(Files.size(mediumFile))
                    .contentType(mediaType)
                    .body(new InputStreamResource(Files.newInputStream(mediumFile)));
        }catch (Exception e) {
            logger.error("An unexpected error occurred while getting a medium", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/media")
    public ResponseEntity<List<URI>> getMedia(){
        logger.info("Received request to get all media");
        try {
            List<Medium> media =  mediumService.getMedia();
            logger.info("length {}", media.size());
            logger.info(media.toString());
          List<URI> uris =  media.stream().map(medium -> MvcUriComponentsBuilder.fromMethodName(MediumController.class, "getMedium", medium.getId().toString()).build().toUri()).collect(Collectors.toList());

            return ResponseEntity.ok(uris);

        }catch (Exception e) {
            logger.error("An unexpected error occurred while getting a medium", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
