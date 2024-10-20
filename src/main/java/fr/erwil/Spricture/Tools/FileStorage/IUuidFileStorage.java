package fr.erwil.Spricture.Tools.FileStorage;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public interface IUuidFileStorage {
    boolean save(File file, UUID uuid);
    InputStream read(UUID uuid);
    void delete(UUID uuid);
}
