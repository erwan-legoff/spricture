package fr.erwil.Spricture.Tools.FileStorage;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface IUuidFileStorage {
    boolean save(MultipartFile file, UUID uuid) throws FileAlreadyExistsException, IOException;
    File read(UUID uuid) throws IOException;
    void delete(UUID uuid);
}
