package fr.erwil.Spricture.Tools.FileStorage;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UuidFileStorageSimple implements IUuidFileStorage{
    private final Path root;

    private Path getPath(UUID uuid){
        return Paths.get(this.root.toString(), uuid.toString());
    }
    public UuidFileStorageSimple(@Value("${file.storage.location}") String storageLocation) {
        this.root = Paths.get(storageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.root);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory", e);
        }
    }

    @Override
    public boolean save(MultipartFile file, UUID uuid) throws IOException, FileAlreadyExistsException {
        Path targetPath = this.getPath(uuid);
        if(Files.exists(targetPath)) throw new FileAlreadyExistsException("A file already exists in " + targetPath);


        Files.copy(file.getInputStream(), targetPath);

        return true;
    }

    @Override
    public InputStream read(UUID uuid) {


        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}

