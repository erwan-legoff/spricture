package fr.erwil.Spricture.Application.Medium.Dtos.Responses;

import lombok.Builder;

import java.net.URL;
import java.util.UUID;

@Builder
public record GetMediumLinkResponseDto(UUID uuid, URL url) {
}
