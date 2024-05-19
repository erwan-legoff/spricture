package fr.erwil.Spricture.Photo;

import fr.erwil.Spricture.File.File;

public class PhotoMapper {

    public static PhotoDto mapToPhotoDto(Photo photo) {
        return new PhotoDto(photo.getId(), photo.getTitle(), photo.getFile().getFilePath());
    }
    public static Photo mapToPhoto(PhotoDto photoDto){
        return new Photo(photoDto.getId(), photoDto.getTitle(), new File(photoDto.getFilePath()));
    }

}
