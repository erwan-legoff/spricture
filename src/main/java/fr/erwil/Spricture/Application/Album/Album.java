package fr.erwil.Spricture.Application.Album;

import fr.erwil.Spricture.Application.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// imports Lombok à ajuster si besoin
@NoArgsConstructor
@Getter
@ToString(exclude = "albumMedia")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "albums")
public class Album extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 255) // ← aligne avec DTO
    private String title;

    @Setter
    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<AlbumMedium> albumMedia = new HashSet<>(); // ← init + pas de setter

    public Album(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public void addMedium(AlbumMedium link) {
        albumMedia.add(link);
        link.setAlbum(this);
    }

    public void removeMedium(AlbumMedium link) {
        albumMedia.remove(link);
        link.setAlbum(null);
    }
}

