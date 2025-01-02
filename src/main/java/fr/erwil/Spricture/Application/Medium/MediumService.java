package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.*;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.Medium.MediumNotFoundException;
import fr.erwil.Spricture.Exceptions.Medium.MediumProcessingException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import fr.erwil.Spricture.Tools.FileStorage.UuidFileStorageSimple;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

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
    public InputStreamResource getFile(GetMediumDto getMediumDto) throws MediumProcessingException {
        Path file = null;
        InputStreamResource resource = null;
        try {
            file = fileStorage.read(getMediumDto.getId());
            resource = new InputStreamResource(Files.newInputStream(file));
        }
        catch (FileNotFoundException e){
            throw new MediumNotFoundException("Medium "+ getMediumDto.getId() + " Not found",e);
        }
        catch (IOException e) {
            throw new MediumProcessingException("Error while getting medium"+ getMediumDto.getId(),e);
        }
        return  resource;


    }

    @Override
    public List<Medium> getMedia() {
        return mediumRepository.findAll();
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
