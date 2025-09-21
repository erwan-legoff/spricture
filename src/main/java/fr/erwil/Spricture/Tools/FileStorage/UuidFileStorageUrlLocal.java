package fr.erwil.Spricture.Tools.FileStorage;

import fr.erwil.Spricture.Application.Medium.MediumFileController;
import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import fr.erwil.Spricture.Exceptions.UuidFileStorage.LocalStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local")
public class UuidFileStorageUrlLocal implements IUuidFileStorageUrl {
    private final UuidFileStorageSimple fileStorage;
    private final String baseUrl;

    public UuidFileStorageUrlLocal(UuidFileStorageSimple fileStorage,
                                   @Value("${file.storage.base-url}") String baseUrl) {
        this.fileStorage = fileStorage;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    @Override
    public boolean save(MultipartFile file, UUID uuid) throws FileAlreadyExistsException {
        try {
            return fileStorage.save(file, uuid);
        } catch (IOException e) {
            throw new LocalStorageException("Could not save file " + uuid, e);
        }
    }

    @Override
    public URL getLink(UUID uuid) {
        try {
            String path = MvcUriComponentsBuilder.fromMethodName(
                    MediumFileController.class,
                    "getFile",
                    uuid.toString()
            ).build().toUriString();
            return new URL(baseUrl + path);
        } catch (MalformedURLException e) {
            throw new LocalStorageException("Invalid URL for file " + uuid, e);
        }
    }

    @Override
    public void delete(UUID uuid) {
        try {
            fileStorage.delete(uuid);
        } catch (IOException e) {
            throw new LocalStorageException("Could not delete file " + uuid, e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            fileStorage.deleteAll();
        } catch (IOException e) {
            throw new LocalStorageException("Could not clean local storage", e);
        }
    }
}
