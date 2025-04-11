package fr.erwil.Spricture.Application.Medium.Dtos.Adapter;

import fr.erwil.Spricture.Application.Medium.Medium;
import org.springframework.web.multipart.MultipartFile;


public class MediumMultipartFileAdapter {
    static public Medium getMedium(MultipartFile multipartFile){
        return  new Medium(multipartFile.getOriginalFilename());
    }
}
