package fr.erwil.Spricture.Application.Medium.MediumUrl;

import fr.erwil.Spricture.Application.Medium.Dtos.Requests.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.GetMediumLinkResponseDto;
import fr.erwil.Spricture.Application.Medium.Medium;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IMediumUrlScopedService {
    Medium create(MultipartFile multipartFile) throws MediumProcessingException;
    CreateManyResponseDto createMany(List<MultipartFile> multipartFiles) throws MediumProcessingException;
    GetMediumLinkResponseDto getURL(UUID uuid);
    List<GetMediumLinkResponseDto> getURLs();
    List<Medium> getMedia();
    void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws EntityNotFoundException, AlreadySoftDeletedException;
    void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws EntityNotFoundException;
}
