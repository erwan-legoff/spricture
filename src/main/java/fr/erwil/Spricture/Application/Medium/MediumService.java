package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.*;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.MediumProcessingException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import fr.erwil.Spricture.Tools.FileStorage.UuidFileStorageSimple;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class MediumService implements  IMediumService {

    private final IUuidFileStorage fileStorage;
    private final  IMediumRepository mediumRepository;
    public MediumService(UuidFileStorageSimple fileStorage, IMediumRepository mediumRepository){
        this.fileStorage = fileStorage;
        this.mediumRepository = mediumRepository;
    }

    @Override
    public Medium create(MultipartFile multipartFile) throws MediumProcessingException {
        Medium mediumToCreate =  MediumMultipartFileAdaptor.getMedium(multipartFile);

        Medium mediumCreated = mediumRepository.save(mediumToCreate);

        try {
            fileStorage.save(multipartFile, mediumCreated.getId());
            return mediumCreated;
        } catch (IOException e) {
            SoftDeleteMediumDto softDeleteDto = SoftDeleteMediumAdaptor.getSoftDeleteMediumDto(mediumCreated);

            this.softDelete(softDeleteDto);
            throw new MediumProcessingException("Error while creating the file : " + multipartFile.getOriginalFilename(), e);
        }
    }

    @Override
    public Path getFile(GetMediumDto getMediumDto) throws MediumProcessingException {
        Path file = null;
        try {
            file = fileStorage.read(getMediumDto.getId());
        } catch (IOException e) {
            throw new MediumProcessingException("Error while getting medium"+ getMediumDto.getId(),e);
        }
        return  file;


    }

    @Override
    public void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws AlreadySoftDeletedException {
        Medium mediumToSoftDelete = mediumRepository.findById(softDeleteMediumDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("The medium " + softDeleteMediumDto.getId() + " was not found before soft delete")
        );

        if(mediumToSoftDelete.getDeletedAt() != null){
            throw new AlreadySoftDeletedException("the medium " + softDeleteMediumDto.getId() + " was already soft deleted");
        }

        mediumToSoftDelete.setDeletedAt(LocalDateTime.now());
    }

    @Override
    public void fullDelete(FullDeleteMediumDto fullDeleteMediumDto) throws MediumProcessingException {
        fileStorage.delete(fullDeleteMediumDto.getId());
    }
}
