package fr.erwil.Spricture.Application.Medium.Dtos.Adapters;

import fr.erwil.Spricture.Application.Medium.Dtos.Responses.GetMediumLinkResponseDto;
import fr.erwil.Spricture.Application.Medium.Medium;

import java.net.URL;

public class GetMediumLinkAdapter {

    public static GetMediumLinkResponseDto from(Medium medium, URL url) {
        return GetMediumLinkResponseDto.builder()
                .id(medium.getId())
                .url(url.toExternalForm())
                .build();
    }
}
