package fr.erwil.Spricture.Photo;

import fr.erwil.Spricture.File.File;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    private Long id;

    @Nullable
    private String title;

    @OneToOne
    File file;

}
