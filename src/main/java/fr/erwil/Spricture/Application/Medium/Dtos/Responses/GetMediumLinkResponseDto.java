package fr.erwil.Spricture.Application.Medium.Dtos.Responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GetMediumLinkResponseDto(UUID id, String url) {
}
