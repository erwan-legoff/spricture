package fr.erwil.Spricture.Application.Medium;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "media")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Medium {
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @Setter
    private Long ownerId;

    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Getter
    @Setter
    private LocalDateTime deletedAt;

    private LocalDateTime originallyCreatedAt;
    
    private String extension;

    
    public Medium(){

    }

    public Medium(String name) {
        this.name = name;
    }

    public Medium(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

}
