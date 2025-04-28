package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.Adapters.MediumMultipartFileAdapter;
import fr.erwil.Spricture.Application.Medium.Dtos.Adapters.SoftDeleteMediumAdapter;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
import fr.erwil.Spricture.Configuration.Security.UserDetails.UserDetailServiceImpl;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import fr.erwil.Spricture.Tools.FileStorage.UuidFileStorageSimple;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MediumOwnerService implements  IMediumService {

    private static final Logger log = LogManager.getLogger(MediumOwnerService.class);

    private final MediumService mediumService;
    private final UserDetailServiceImpl userDetailService;
    public MediumOwnerService(MediumService mediumService, UserDetailServiceImpl userDetailService){

        this.mediumService = mediumService;
        this.userDetailService = userDetailService;
    }

    @Transactional
    @Override
    public Medium create(MultipartFile multipartFile) throws MediumProcessingException {
        return this.mediumService.create(multipartFile, userDetailService.getCurrentUserDetails().getId());
    }


    @Override
    public CreateManyResponseDto createMany(List<MultipartFile> multipartFiles) throws MediumProcessingException {
        return this.mediumService.createMany(multipartFiles, userDetailService.getCurrentUserDetails().getId());
    }

    @Override
    public InputStreamResource getFile(GetMediumDto getMediumDto) throws MediumProcessingException {
        return this.mediumService.getFile(getMediumDto);
    }

    @Override
    public List<Medium> getMedia() {
        return this.mediumService.getMedia(this.userDetailService.getCurrentUserDetails().getId());
    }

    @Override
    public void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws AlreadySoftDeletedException {
      this.mediumService.softDelete(softDeleteMediumDto);
    }

    @Override
    public void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws MediumProcessingException {
        this.mediumService.fullDelete(fullDeleteMediumDto);
    }


}
