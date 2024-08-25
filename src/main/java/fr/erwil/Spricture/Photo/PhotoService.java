package fr.erwil.Spricture.Photo;

import fr.erwil.Spricture.File.File;
import fr.erwil.Spricture.File.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private Environment environment;

    public Optional<PhotoDto> getPhotoById(Long photoId){
        Optional<Photo> photo = photoRepository.findById(photoId);
        return photo.map(PhotoMapper::mapToPhotoDto);

    }

    /**
     * Create a photo and its corresponding file, and save them
     * The transactional annotation means that if there is an error saving the file or the photo, it will cancel both,
     * because it's a single DB operation
     * @param photoDto the photo to be saved
     * @return the created photo dto
     */
    @Transactional
    public PhotoDto createPhoto(PhotoDto photoDto){
        Photo photo =  PhotoMapper.mapToPhoto(photoDto);
        File photoFile = photo.getFile();

        fileRepository.save(photoFile);
        PhotoDto createdPhoto = PhotoMapper.mapToPhotoDto(photoRepository.save(photo));
        return createdPhoto;
    }

    public void createPhotoDumb(MultipartFile photo){
        Path projectDirectory = Paths.get(Objects.requireNonNull(environment.getProperty("user.dir")));
        Path directory = Paths.get(projectDirectory.toString(),"photos");
        File photoFile = new File(directory.toString(), photo.getOriginalFilename());
        try {
            createFile(directory.toString(), photo.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileRepository.save(photoFile);

        try {
            saveMultipartFile(photo, directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Create an empty file ready to be written
     * @param directory where to stock the file
     * @param fileName the name of the written file
     * @return the full path of the created file
     * @throws IOException when file exception
     */
    public Path createFile(String directory, String fileName) throws IOException {
        Path directoryPath = Paths.get(directory);
        if(!Files.exists(directoryPath)){
            // creates the specified directory and all its parents
            Files.createDirectories(directoryPath);
        }
        Path filePath = directoryPath.resolve(fileName);
        if(!Files.exists(filePath)){
            Files.createFile(filePath);
        }

        return filePath;
    }

    private void saveMultipartFile(MultipartFile multipartFile, Path directory) throws IOException {

        Path filePath = Paths.get(directory.toString(), multipartFile.getOriginalFilename());
        java.io.File newFile = null;

        newFile = new java.io.File(filePath.toString());
        boolean isCreated = newFile.createNewFile();

        System.out.println("Bien créé ? : "+ isCreated);



        multipartFile.transferTo(filePath);
    }

    /**
     * @return all photos
     */
    public Iterable<PhotoDto> getAllPhotos(){
        Iterable<PhotoDto> photoDtos = photoRepository.findAll().stream() //allow to change records
                .map(PhotoMapper::mapToPhotoDto)// mapping conversion
                .collect(Collectors.toList()); // we want a list
        return photoDtos;
    }

//    public Iterable<MultipartFile> getAllPhotosDumb(){
//        Iterable<File> files // we want a list
//        return files;
//    }
}
