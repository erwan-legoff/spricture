package fr.erwil.Spricture.Application.Medium.Dtos.Adapter;

import fr.erwil.Spricture.Application.Medium.Dtos.Request.SoftDeleteMediumDto;
import fr.erwil.Spricture.Application.Medium.Medium;

public class SoftDeleteMediumAdapter {
    public static Medium getMedium(SoftDeleteMediumDto fullDeleteMediumDto){
        return null;
    }

    public static SoftDeleteMediumDto getSoftDeleteMediumDto(Medium medium){
        return new SoftDeleteMediumDto(medium.getId());
    }
}
