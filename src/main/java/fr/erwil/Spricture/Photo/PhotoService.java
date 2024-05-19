package fr.erwil.Spricture.Photo;

import fr.erwil.Spricture.File.File;
import fr.erwil.Spricture.File.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    // Transactional mean that all operations are operated in one transaction
    @Transactional
    public PhotoDto createPhoto(PhotoDto photoDto){
        Photo photo =  PhotoMapper.mapToPhoto(photoDto);
        File photoFile = photo.getFile();

        fileRepository.save(photoFile);

        return PhotoMapper.mapToPhotoDto(photoRepository.save(photo));
    }

    public Iterable<Photo> getAllPhotos(){
        return photoRepository.findAll();
    }
}
