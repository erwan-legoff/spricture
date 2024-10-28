package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.SoftDeleteMediumDto;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.MediumProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface IMediumService {
    Medium create(MultipartFile multipartFile) throws MediumProcessingException;
    InputStream getFile(GetMediumDto getMediumDto) throws EntityNotFoundException, MediumProcessingException;
    void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws EntityNotFoundException, AlreadySoftDeletedException;
    void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws EntityNotFoundException, IOException;
}
