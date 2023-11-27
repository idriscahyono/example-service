package idriscahyono.exampleservice.service;

import io.minio.ObjectWriteResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    ObjectWriteResponse uploadFile(MultipartFile file, String folderName);
    byte[] getFile(String objectName);
}
