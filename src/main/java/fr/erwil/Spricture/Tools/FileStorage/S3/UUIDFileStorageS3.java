package fr.erwil.Spricture.Tools.FileStorage.S3;

import fr.erwil.Spricture.Exceptions.UuidFileStorage.UploadException;
import fr.erwil.Spricture.Tools.FileStorage.IUuidFileStorage;
import fr.erwil.Spricture.Exceptions.UuidFileStorage.FileAlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Primary
@Qualifier("s3")
@Service
public class UUIDFileStorageS3 implements IUuidFileStorage {
    private static final Logger log = LogManager.getLogger(UUIDFileStorageS3.class);
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

        Path targetFile = Files.createTempFile(
                "photone_", // Préfixe
                "_" + uuid + "_" + UUID.randomUUID() + ".tmp"
        );

        targetFile.toFile().deleteOnExit();

        downloadFile(uuid, targetFile);

        return targetFile;
    }

    private void downloadFile(UUID uuid, Path targetFile) throws IOException {

        try (OutputStream outputStream = Files.newOutputStream(targetFile)) { // Prepare the outputStream
            // AWS download request
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(properties.getBucket())
                    .key(uuid.toString())
                    .build();

            // Write the downloaded file into the temp file
            s3Client.getObject(getObjectRequest, ResponseTransformer.toOutputStream(outputStream));

        } catch (Exception e) {
            Files.deleteIfExists(targetFile);
            throw new IOException("Failed to download file from S3: " + uuid, e);
        }
    }


    @Override
    public void delete(UUID uuid) throws IOException {

    }


    @Override
    public void deleteAll() {
        deleteAllTempFiles();
        deleteAllInBucket();
    }

    @Profile("dev")
    private void deleteAllInBucket() {
        log.warn("Deleting all BUCKET files!");
        var s3Objects = s3Client.listObjectsV2(req->req.bucket(properties.getBucket()));
        var streamedObjects = s3Objects.contents().stream();
        List<ObjectIdentifier> keys = streamedObjects.map(s3Object -> ObjectIdentifier.builder().key(s3Object.key()).build()).toList();
        try {
            DeleteObjectsRequest request = DeleteObjectsRequest.builder().bucket(properties.getBucket()).delete(delete -> delete.objects(keys)).build();
            s3Client.deleteObjects(request);
        } catch (AwsServiceException |  SdkClientException e) {
            throw new UploadException("Error while deleting aws files.",e);
        }
    }

    private static void deleteAllTempFiles() {
        log.warn("Deleting all temp files!");
        Path tmpDir = Path.of(System.getProperty("java.io.tmpdir"));

        try (Stream<Path> files = Files.list(tmpDir)) {
            files
                    .filter(path -> path.getFileName().toString().startsWith("photone_"))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            log.warn("Could not delete temp file: {}", path, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean temp directory", e);
        }
    }

}
