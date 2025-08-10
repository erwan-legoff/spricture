package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.Requests.GetMediumDto;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MediumFileController {
    private final IMediumScopedService mediumService;

    public MediumFileController(IMediumScopedService mediumService) {
        this.mediumService = mediumService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/files/{id}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String id) throws MediumProcessingException {
        InputStreamResource resource = mediumService.getFile(new GetMediumDto(UUID.fromString(id)));
        return ResponseEntity.ok(resource);
    }
}
