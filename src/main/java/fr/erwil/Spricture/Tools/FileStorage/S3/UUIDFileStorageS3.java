package fr.erwil.Spricture.Tools.FileStorage.S3;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.UploadException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class UUIDFileStorageS3 implements IUuidFileStorage {
    private final S3Client s3Client;
    private final S3StorageProperties properties;

    public UUIDFileStorageS3(S3Client s3Client, S3StorageProperties properties) {
        this.s3Client = s3Client;
        this.properties = properties;
    }

    @Override

    public boolean save(MultipartFile file, UUID uuid) {
        String key = uuid.toString();
        checkObjectDoesNotExist(key);
        uploadObject(key, file);
        return true;
    }
    private void checkObjectDoesNotExist(String key) {
        try {
            s3Client.headObject(req -> req
                    .bucket(properties.getBucket())
                    .key(key));
            throw new FileAlreadyExistsException("A file with UUID " + key + " already exists in bucket " + properties.getBucket());
        } catch (S3Exception e) {
            if (e.statusCode() != 404) {
                throw new UploadException("Error while checking if file " + key + " exists in bucket " + properties.getBucket(), e);
            }
        }
    }
    private void uploadObject(String key, MultipartFile file) {
        try {
            RequestBody body = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
            s3Client.putObject(
                    req -> req
                            .bucket(properties.getBucket())
                            .key(key),
                    body
            );
        } catch (IOException | SdkClientException | AwsServiceException e) {
            throw new UploadException("Failed to upload file with UUID " + key + " to bucket " + properties.getBucket(), e);
        }
    }



    @Override
    public Path read(UUID uuid) throws IOException {
        Path target = Files.createTempFile("photone_", "_" + uuid);
        target.toFile().deleteOnExit();
        s3Client.getObject(
                req -> req
                        .bucket(properties.getBucket())
                        .key(uuid.toString()),
                target
        );
        return target;
    }

    @Override
    public void delete(UUID uuid) throws IOException {

    }

    @Override
    public void deleteAll() throws IOException {

    }
}
