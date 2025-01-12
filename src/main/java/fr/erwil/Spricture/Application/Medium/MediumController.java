package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.CreateManyResponseDto;
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
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class MediumController {
    private static final Logger logger = LoggerFactory.getLogger(MediumController.class);

    private final IMediumService mediumService;

    public MediumController(IMediumService mediumService){
        this.mediumService=mediumService;
    }

    @RequestMapping("/hello")
    public @ResponseBody String greeting() {
        return "Hello World!";
    }

    @PostMapping("/medium")
    public ResponseEntity<Medium> createMedium(@RequestParam("medium") @NotNull  MultipartFile medium) {
        logger.info("Received request to create new medium");

        Medium createdMedium = createOneMedium(medium);

        return new ResponseEntity<>(createdMedium, HttpStatus.CREATED);

    }

    private Medium createOneMedium(MultipartFile medium) {
        logger.debug("File details - Name: {}, Size: {} bytes", medium.getOriginalFilename(), medium.getSize());

        Medium createdMedium = mediumService.create(medium);

        logger.info("Successfully created medium with ID: {}", createdMedium.getId());
        return createdMedium;
    }

    @PostMapping("/media")
    public ResponseEntity<CreateManyResponseDto> createMedia(@RequestParam("media") @NotNull  List<MultipartFile> media) {
        logger.info("Received request to create several Media");
        CreateManyResponseDto response = mediumService.createMany(media);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/medium")
    public ResponseEntity<Resource> getMedium(@RequestParam("id") @NotNull @NotEmpty String id){
        logger.info("Received request to get a medium");
            GetMediumDto getMediumDto = new GetMediumDto(UUID.fromString(id));
            InputStreamResource resource =  mediumService.getFile(getMediumDto);

            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
    }

    @GetMapping("/media")
    public ResponseEntity<List<URI>> getMedia(){
        logger.info("Received request to get all media");
        try {
            List<Medium> media =  mediumService.getMedia();
            logger.info("length {}", media.size());
            logger.info(media.toString());
          List<URI> uris =  media
                  .stream()
                  .map(getMediumToUriFunction())
                  .collect(Collectors.toList());

            return ResponseEntity.ok(uris);

        }catch (Exception e) {
            logger.error("An unexpected error occurred while getting a medium", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This create an URI for each medium that points to the correct getMedium endpoint
     * @return
     */
    private static Function<Medium, URI> getMediumToUriFunction() {
        return medium -> MvcUriComponentsBuilder.fromMethodName(
                MediumController.class,
                "getMedium",
                medium.getId().toString()
        ).build().toUri();
    }

}
