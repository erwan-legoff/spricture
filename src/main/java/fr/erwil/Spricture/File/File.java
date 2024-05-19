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
}
