package fr.erwil.Spricture.Tools.FileStorage;

import java.io.File;
import java.util.UUID;

public interface IUuidFileStorage {
    boolean save(File file, UUID uuid);
    File read(UUID uuid);
}
