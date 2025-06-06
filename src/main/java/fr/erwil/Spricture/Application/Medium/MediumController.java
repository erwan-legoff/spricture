package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.GetMediumLinkResponseDto;
import fr.erwil.Spricture.Application.Medium.MediumUrl.IMediumUrlScopedService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@RestController
public class MediumController {
    private static final Logger logger = LoggerFactory.getLogger(MediumController.class);

    private final IMediumUrlScopedService mediumService;

    public MediumController(IMediumUrlScopedService mediumService){
        this.mediumService=mediumService;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/hello")
    public @ResponseBody String greeting() {
        return "Hello World!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/hello-admin")
    public @ResponseBody String greetingAdmin() {
        return "Hello Admin World!";
    }

    @PostMapping("/medium")
    public ResponseEntity<Medium> createMedium(@RequestParam("medium") @NotNull  MultipartFile medium) {
        logger.info("Received request to create new medium");

        Medium createdMedium = mediumService.create(medium);

        return new ResponseEntity<>(createdMedium, HttpStatus.CREATED);

    }


    @PostMapping("/media")
    public ResponseEntity<CreateManyResponseDto> createMedia(@RequestParam("media") @NotNull  List<MultipartFile> media) {
        logger.info("Received request to create several Media");
        CreateManyResponseDto response = mediumService.createMany(media);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/medium")
    public ResponseEntity<GetMediumLinkResponseDto> getMedium(@RequestParam("id") @NotNull @NotEmpty String id){
        logger.info("Received request to get a medium");
            return ResponseEntity.ok()
                    .body(mediumService.getURL(UUID.fromString(id)));
    }

    @GetMapping("/media")
    public ResponseEntity<List<GetMediumLinkResponseDto>> getMedia(){
        logger.info("Received request to get all media");
        return ResponseEntity.ok()
                .body(mediumService.getURLs());
    }

    /**
     * This creates a URI for each medium that points to the correct getMedium endpoint
     * @return
     */
    private static Function<Medium, URI> getMediumToUriFunction() {
        return medium -> MvcUriComponentsBuilder.fromMethodName(
                MediumController.class,
                "getMedium",
                medium.getId().toString()
        ).build().toUri();
    }

    @DeleteMapping("/medium")
    public ResponseEntity<Void> softDeleteMedium(@RequestBody @NotNull SoftDeleteMediumDto softDeleteMediumDto) {
        logger.info("Soft deleting medium {}", softDeleteMediumDto.id());
        mediumService.softDelete(softDeleteMediumDto);
        return ResponseEntity.noContent().build();
    }


}
