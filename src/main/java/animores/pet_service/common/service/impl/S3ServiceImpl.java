package animores.pet_service.common.service.impl;

import animores.pet_service.common.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Async
    public void uploadFilesToS3(List<MultipartFile> files, String path, List<String> fileNames)
        throws IOException {

        for (int i = 0; i < files.size(); i++) {
            uploadFileToS3(files.get(i), path, fileNames.get(i));
        }
        log.info("All files uploaded to S3");
    }

    public void uploadFileToS3(MultipartFile file, String path, String fileName) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .contentType(file.getContentType())
            .contentLength(file.getSize())
            .key(path + fileName + resolveExtension(file.getContentType()))
            .build();
        RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
        s3Client.putObject(putObjectRequest, requestBody);
        log.info("File uploaded to S3: {}", putObjectRequest.key());
    }

    public void removeFilesFromS3(List<String> urls) {
        List<ObjectIdentifier> keys = urls.stream()
            .map(url -> ObjectIdentifier.builder().key(url).build())
            .toList();

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
            .bucket(bucketName)
            .delete(b -> b.objects(keys))
            .build();

        s3Client.deleteObjects(deleteObjectsRequest);
    }

    private String resolveExtension(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "video/mp4" -> ".mp4";
            default -> throw new IllegalArgumentException("Unsupported file type: " + contentType);
        };
    }
}
