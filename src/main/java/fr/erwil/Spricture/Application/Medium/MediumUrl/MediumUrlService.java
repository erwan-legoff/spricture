package fr.erwil.Spricture.Application.Medium.MediumUrl;

import fr.erwil.Spricture.Application.Medium.Dtos.Requests.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.GetMediumLinkResponseDto;
import fr.erwil.Spricture.Application.Medium.IMediumRepository;
import fr.erwil.Spricture.Application.Medium.Medium;
import fr.erwil.Spricture.Application.Medium.MediumService;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorageUrl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class MediumUrlService implements IMediumUrlService{

    private final MediumService mediumService;
    private final IUuidFileStorageUrl fileStorageUrl;
    private final IMediumRepository mediumRepository;

    public MediumUrlService(MediumService mediumService, IUuidFileStorageUrl fileStorageUrl, IMediumRepository mediumRepository) {
        this.mediumService = mediumService;
        this.fileStorageUrl = fileStorageUrl;
        this.mediumRepository = mediumRepository;
    }

    @Override
    public Medium create(MultipartFile multipartFile, long ownerId) throws MediumProcessingException {
        return mediumService.create(multipartFile, ownerId);
    }

    @Override
    public CreateManyResponseDto createMany(List<MultipartFile> multipartFiles, long ownerId) throws MediumProcessingException {
        return mediumService.createMany(multipartFiles, ownerId);
    }

    @Override
    public GetMediumLinkResponseDto getURL(UUID uuid) {
        return GetMediumLinkResponseDto.builder().url(fileStorageUrl.getLink(uuid)).uuid(uuid).build();
    }

    @Override
    public List<GetMediumLinkResponseDto> getURLs(long ownerId) {
        List<UUID> uuids = mediumRepository.findIdByOwnerIdAndDeletedAtIsNull(ownerId);
        return uuids.stream().map(this::getURL).toList();
    }

    @Override
    public List<Medium> getMedia(long ownerId) {
        return mediumService.getMedia(ownerId);
    }

    @Override
    public void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws EntityNotFoundException, AlreadySoftDeletedException {
        mediumService.softDelete(softDeleteMediumDto);
    }

    @Override
    public void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws EntityNotFoundException {
        mediumService.fullDelete(fullDeleteMediumDto);
    }
}
