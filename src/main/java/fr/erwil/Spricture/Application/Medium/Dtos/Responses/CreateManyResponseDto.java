package fr.erwil.Spricture.Application.Medium.Dtos.Responses;

import fr.erwil.Spricture.Application.Medium.Medium;

import java.util.List;

public class CreateManyResponseDto {
    private final List<Medium> createdMedia;
    private final List<String> notCreatedFileNames;
    private final boolean allSucceeded;

    public CreateManyResponseDto(List<Medium> createdMedia, List<String> notCreatedFileNames) {
        this.createdMedia = createdMedia;
        this.notCreatedFileNames = notCreatedFileNames;
        this.allSucceeded = notCreatedFileNames.isEmpty();
    }

    public List<Medium> getCreatedMedia() {
        return createdMedia;
    }

    public List<String> getNotCreatedFileNames() {
        return notCreatedFileNames;
    }

    public boolean isAllSucceeded() {
        return allSucceeded;
    }
}
