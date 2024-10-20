package fr.erwil.Spricture.Application.Medium;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "media")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Medium {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

    private LocalDateTime originallyCreatedAt;
    
    private String extension;

    
    public Medium(){

    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
