package fr.erwil.Spricture.Application.Medium;

import fr.erwil.Spricture.Application.Medium.Dtos.DeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.GetMediumDto;
import fr.erwil.Spricture.Application.Medium.Dtos.SoftDeleteMediumDto;
import fr.erwil.Spricture.Exceptions.AlreadySoftDeletedException;
import fr.erwil.Spricture.Exceptions.MediumProcessingException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MediumService implements  IMediumService {
    @Override
    public Medium create(MultipartFile multipartFile) throws MediumProcessingException {
        return null;
    }

    @Override
    public InputStream get(GetMediumDto getMediumDto) throws MediumProcessingException {
        return null;
    }

    @Override
    public void softDelete(SoftDeleteMediumDto softDeleteMediumDto) throws AlreadySoftDeletedException {

    }

    @Override
    public void fullDelete(DeleteMediumDto deleteMediumDto) throws MediumProcessingException {

    }
}
