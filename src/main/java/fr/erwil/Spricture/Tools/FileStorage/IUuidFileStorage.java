package fr.erwil.Spricture.Tools.FileStorage;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface IUuidFileStorage {
    boolean save(MultipartFile file, UUID uuid) throws FileAlreadyExistsException, IOException;
    Path read(UUID uuid) throws IOException;
    void delete(UUID uuid);

    void deleteAll() throws IOException;
}
