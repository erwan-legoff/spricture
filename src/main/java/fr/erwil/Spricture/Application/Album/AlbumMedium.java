package fr.erwil.Spricture.Application.Album;

import fr.erwil.Spricture.Application.AbstractEntity;
import fr.erwil.Spricture.Application.Medium.Medium;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "album_media")
public class AlbumMedium extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "medium_id", nullable = false)
    private Medium medium;

}
