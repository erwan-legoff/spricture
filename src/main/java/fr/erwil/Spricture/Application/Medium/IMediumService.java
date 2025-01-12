package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.CreateManyResponseDto;
import fr.erwil.Spricture.Application.Medium.Dtos.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.SoftDeleteMediumDto;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IMediumService {
    Medium create(MultipartFile multipartFile) throws MediumProcessingException;
    CreateManyResponseDto createMany(List<MultipartFile> multipartFiles) throws MediumProcessingException;
    InputStreamResource getFile(GetMediumDto getMediumDto) throws MediumException;
    List<Medium> getMedia();
    void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws EntityNotFoundException, AlreadySoftDeletedException;
    void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws EntityNotFoundException, IOException;
}
