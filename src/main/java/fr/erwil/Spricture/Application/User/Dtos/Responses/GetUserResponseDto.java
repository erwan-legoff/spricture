package fr.erwil.Spricture.Application.User.Dtos.Responses;

import fr.erwil.Spricture.Application.User.UserRole;
import fr.erwil.Spricture.Application.User.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponseDto {
    private Long id;
    private String pseudo;
    private String name;
    private String lastName;
    private String email;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    private Long mediaCount;
}
