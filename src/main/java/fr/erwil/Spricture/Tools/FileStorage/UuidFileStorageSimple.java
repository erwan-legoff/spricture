package fr.erwil.Spricture.Tools.FileStorage;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UuidFileStorageSimple implements IUuidFileStorage{
    private final Path root;

    private Path getPath(UUID uuid){
        Path path = Paths.get(this.root.toString(), uuid.toString()).toAbsolutePath().normalize();
        if(!path.getParent().equals(root.toAbsolutePath())) throw new SecurityException("Error while creating path, the parent is not the root");
        return path;
    }
    public UuidFileStorageSimple(FileStorageProperties properties) {

        this.root = Paths.get(properties.getLocation()).toAbsolutePath().normalize();

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
    public Path read(UUID uuid) throws FileNotFoundException {
        Path file = this.getPath(uuid);
        if(!Files.exists(file)) throw new FileNotFoundException("Error while reading the file named "+ uuid);

        return file;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void deleteAll() throws IOException {
        FileUtils.cleanDirectory(root.toFile());
    }
}

