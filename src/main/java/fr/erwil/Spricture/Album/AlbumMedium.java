package fr.erwil.Spricture.Album;

import fr.erwil.Spricture.Medium.Medium;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "album_media")
public class AlbumMedium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "medium_id", nullable = false)
    private Medium medium;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime addedAt;
}
