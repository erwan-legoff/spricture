package fr.erwil.Spricture.Photo;

import fr.erwil.Spricture.File.File;
import fr.erwil.Spricture.File.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private FileRepository fileRepository;

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

    /**
     * @return all photos
     */
    public Iterable<PhotoDto> getAllPhotos(){
        Iterable<PhotoDto> photoDtos = photoRepository.findAll().stream() //allow to change records
                .map(PhotoMapper::mapToPhotoDto)// mapping conversion
                .collect(Collectors.toList()); // we want a list
        return photoDtos;
    }
}
