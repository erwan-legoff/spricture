package fr.erwil.Spricture.Tools.FileStorage;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.UUID;

public interface IUuidFileStorageUrl {
    boolean save(MultipartFile file, UUID uuid) throws FileAlreadyExistsException;
    URL getLink(UUID uuid);
    void delete(UUID uuid);

    void deleteAll();
}
