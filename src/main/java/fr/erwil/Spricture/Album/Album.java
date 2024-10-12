package fr.erwil.Spricture.Album;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "albums")
public class Album {
    @GeneratedValue
    @Id
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    private String title;

    private String description;
}
