package fr.erwil.Spricture.Application.Medium.Dtos;

import fr.erwil.Spricture.Application.Medium.Medium;
import org.springframework.web.multipart.MultipartFile;


public class MediumMultipartFileAdaptor {
    static public Medium getMedium(MultipartFile multipartFile){
        return  new Medium(multipartFile.getOriginalFilename());
    }

}
