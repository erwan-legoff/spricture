package fr.erwil.Spricture.Application.Medium;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMediumService {

    Medium create(MultipartFile multipartFile, Long ownerId) throws MediumProcessingException;

    CreateManyResponseDto createMany(List<MultipartFile> multipartFiles, Long ownerId) throws MediumProcessingException;

    InputStreamResource getFile(GetMediumDto getMediumDto) throws MediumProcessingException;

    List<Medium> getMedia(Long ownerId);

    void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws AlreadySoftDeletedException;

    void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws MediumProcessingException;
}
