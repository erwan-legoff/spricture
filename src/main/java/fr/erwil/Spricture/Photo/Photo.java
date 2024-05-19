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

    public Photo(Long id, @Nullable String title, File file) {
        this.id = id;
        this.title = title;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @OneToOne
    File file;

}
