package fr.erwil.Spricture.File;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class File {
    @GeneratedValue
    @Id
    Long id;

    String path;

    String name;

    public File(){

    }

    public File(Long id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    public File(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath(){
        return path + "/" + name;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
