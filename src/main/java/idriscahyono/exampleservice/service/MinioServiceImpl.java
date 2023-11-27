package idriscahyono.exampleservice.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.defaultBucket}")
    private String minioBucket;

    @Value("${minio.defaultFolder}")
    private String minioFolder;

    @Override
    public ObjectWriteResponse uploadFile(MultipartFile file, String folderName) {
        if (folderName != null){
            minioFolder = folderName;
        }

        try{
            // Convert MultipartFile to Input File
            InputStream inputStream = file.getInputStream();

            String contentType = Objects.requireNonNull(file.getContentType()).split("/")[1];

            // Object Name
            String objectName = minioFolder + "/" + UUID.randomUUID() + "." + contentType;

            // Upload File to Minio
            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioBucket)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            inputStream.close();

            return response;
        }catch (Exception e){
            System.out.println("UPLOAD TO MINIO GOT ERROR" + e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] getFile(String objectName) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioBucket)
                            .object(objectName)
                            .build()
            );

            return inputStream.readAllBytes();
        }catch (Exception e){
            System.out.println("GET FILE FROM MINIO GOT ERROR" + e.getMessage());
            return null;
        }
    }
}
