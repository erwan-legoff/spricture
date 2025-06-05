package fr.erwil.Spricture.Tools.FileStorage.S3;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorageUrl;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.UUID;

public class UuidFileStorageUrlS3 implements IUuidFileStorageUrl {
    private final UUIDFileStorageS3 fileStorage;
    private final S3LinkSigner linkSigner;
    private final S3StorageProperties properties;

    public UuidFileStorageUrlS3(UUIDFileStorageS3 fileStorage, S3LinkSigner linkSigner, S3StorageProperties properties) {
        this.fileStorage = fileStorage;
        this.linkSigner = linkSigner;
        this.properties = properties;
    }

    @Override
    public boolean save(MultipartFile file, UUID uuid) throws FileAlreadyExistsException {
        return fileStorage.save(file, uuid);
    }

    // TODO : Passer en String :)
    @Override
    public URL getLink(UUID uuid) {
        return linkSigner.createPresignedGetUrl(properties.getBucket(),uuid.toString());
    }

    @Override
    public void delete(UUID uuid) {
        fileStorage.delete(uuid);
    }

    @Override
    public void deleteAll() {
        fileStorage.deleteAll();
    }
}
