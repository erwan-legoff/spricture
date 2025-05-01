package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.Adapters.MediumMultipartFileAdapter;
import fr.erwil.Spricture.Application.Medium.Dtos.Adapters.SoftDeleteMediumAdapter;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.FullDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Requests.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.Responses.CreateManyResponseDto;
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
public class MediumService  {

    private static final Logger log = LogManager.getLogger(MediumService.class);
    private final IUuidFileStorage fileStorage;
    private final  IMediumRepository mediumRepository;
    public MediumService(UuidFileStorageSimple fileStorage, IMediumRepository mediumRepository){
        this.fileStorage = fileStorage;
        this.mediumRepository = mediumRepository;
    }

    @Transactional
    public Medium create(MultipartFile multipartFile, Long ownerId) throws MediumProcessingException {
        Medium mediumToCreate =  MediumMultipartFileAdapter.getMedium(multipartFile);
        mediumToCreate.setOwnerId(ownerId);

        Medium mediumCreated = mediumRepository.save(mediumToCreate);

        try {
            fileStorage.save(multipartFile, mediumCreated.getId());
            return mediumCreated;
        } catch (IOException e) {
            SoftDeleteMediumDto softDeleteDto = SoftDeleteMediumAdapter.getSoftDeleteMediumDto(mediumCreated);

            this.softDelete(softDeleteDto);
            throw new MediumProcessingException("Error while creating the file : " + multipartFile.getOriginalFilename(), e);
        }
    }



    public CreateManyResponseDto createMany(List<MultipartFile> multipartFiles, Long ownerId) throws MediumProcessingException {
        List<Medium> createdMedia = new ArrayList<>();
        List<String> notCreatedFileNames = new ArrayList<>();
        for(MultipartFile file : multipartFiles){
            try {
                Medium medium = this.create(file, ownerId);
                createdMedia.add(medium);
            } catch (Exception e) {
                log.warn("{} medium was not created because of this error: {}", file.getOriginalFilename(), e.getMessage(), e);
                notCreatedFileNames.add(file.getOriginalFilename());
            }
        }


        return new CreateManyResponseDto(createdMedia, notCreatedFileNames);
    }


    public InputStreamResource getFile(GetMediumDto getMediumDto) throws MediumProcessingException {
        Path file = null;
        InputStreamResource resource = null;
        try {
            file = fileStorage.read(getMediumDto.id());
            resource = new InputStreamResource(Files.newInputStream(file));
        }
        catch (FileNotFoundException e){
            throw new MediumNotFoundException("Medium "+ getMediumDto.id() + " Not found",e);
        }
        catch (IOException e) {
            throw new MediumProcessingException("Error while getting medium"+ getMediumDto.id(),e);
        }
        return  resource;


    }


    public List<Medium> getMedia(Long ownerId) {
        return mediumRepository.findByOwnerIdAndDeletedAtIsNull(ownerId);
    }

    @Transactional
    public void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws AlreadySoftDeletedException {
        Medium mediumToSoftDelete = mediumRepository.findById(softDeleteMediumDto.id()).orElseThrow(
                () -> new EntityNotFoundException("The medium " + softDeleteMediumDto.id() + " was not found before soft delete")
        );

        if (mediumToSoftDelete.getDeletedAt() != null) {
            throw new AlreadySoftDeletedException("the medium " + softDeleteMediumDto.id() + " was already soft deleted");
        }

        mediumToSoftDelete.setDeletedAt(LocalDateTime.now());
        mediumRepository.save(mediumToSoftDelete);

    }


    public void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws MediumProcessingException {
        try {
            UUID mediumId = fullDeleteMediumDto.getId();
            if (mediumRepository.existsById(mediumId)) {
                mediumRepository.deleteById(mediumId);
            }
            fileStorage.delete(mediumId);
        } catch (IOException e) {
            throw new MediumProcessingException("Error while fulldeleting " + fullDeleteMediumDto.getId(), e);
        } catch (Exception e) {
            throw new RuntimeException("unexpected error while fulldeleting" + fullDeleteMediumDto.getId());
        }
    }


}
