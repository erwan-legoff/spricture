package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.DeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.SoftDeleteMediumDto;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface IMediumService {
    Medium create(MultipartFile multipartFile) throws IOException;
    InputStream get(GetMediumDto getMediumDto) throws IOException;
    void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws EntityNotFoundException, AlreadySoftDeletedException;
    void fullDelete(DeleteMediumDto deleteMediumDto) throws EntityNotFoundException, IOException;
}
