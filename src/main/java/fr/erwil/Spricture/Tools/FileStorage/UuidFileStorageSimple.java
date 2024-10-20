package fr.erwil.Spricture.Tools.FileStorage;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class UuidFileStorageSimple implements IUuidFileStorage{
    @Override
    public boolean save(File file, UUID uuid) {
        return false;
    }

    @Override
    public File read(UUID uuid) {
        return null;
    }


}

