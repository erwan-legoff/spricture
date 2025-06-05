package fr.erwil.Spricture.Application.Medium.MediumUrl;

import fr.erwil.Spricture.Application.Medium.Dtos.Requests.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.GetMediumLinkResponseDto;
import fr.erwil.Spricture.Application.Medium.Medium;
import fr.erwil.Spricture.Configuration.Security.UserDetails.UserDetailsServiceImpl;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.UUID;
@Service
public class MediumUrlScopedService implements IMediumUrlScopedService {
    private final IMediumUrlService mediumUrlService;
    private final UserDetailsServiceImpl userDetailService;
    public MediumUrlScopedService(IMediumUrlService mediumUrlService, UserDetailsServiceImpl userDetailService) {
        this.mediumUrlService = mediumUrlService;
        this.userDetailService = userDetailService;
    }

    @Override
    public Medium create(MultipartFile multipartFile) throws MediumProcessingException {
        return mediumUrlService.create(multipartFile, getCurrentUserId());
    }



    @Override
    public CreateManyResponseDto createMany(List<MultipartFile> multipartFiles) throws MediumProcessingException {
        return mediumUrlService.createMany(multipartFiles, getCurrentUserId());
    }

    @Override
    public GetMediumLinkResponseDto getURL(UUID uuid) {
        return mediumUrlService.getURL(uuid);
    }

    @Override
    public List<GetMediumLinkResponseDto> getURLs() {
        return mediumUrlService.getURLs(getCurrentUserId());
    }

    @Override
    public List<Medium> getMedia() {
        return mediumUrlService.getMedia(getCurrentUserId());
    }

    @Override
    public void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws EntityNotFoundException, AlreadySoftDeletedException {
        mediumUrlService.softDelete(softDeleteMediumDto);
    }

    @Override
    public void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws EntityNotFoundException {
        mediumUrlService.fullDelete(fullDeleteMediumDto);
    }

    private Long getCurrentUserId() {
        return userDetailService.getCurrentUserDetails().getId();
    }
}
