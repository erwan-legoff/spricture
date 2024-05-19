package fr.erwil.Spricture.File;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class File {
    @GeneratedValue
    @Id
    Long id;

    String filePath;

    public File(){

    }

    public File(Long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public File(String filePath) {
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
