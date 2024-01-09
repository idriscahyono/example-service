package idriscahyono.exampleservice.service;

import idriscahyono.exampleservice.base.BaseResponse;
import idriscahyono.exampleservice.entity.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentServiceImpl extends AppService implements DocumentService{

    @Override
    public BaseResponse findById(Long id) {
        return null;
    }

    @Override
    public BaseResponse getFile(UUID id) {
        Optional<Document> getDocumentById = documentRepository.findById(id);
        if (getDocumentById.isEmpty()){
             return BaseResponse.builder().code(HttpStatus.UNPROCESSABLE_ENTITY.value()).file(null).build();
        }

        byte[] docFile = minioService.getFile(getDocumentById.get().getPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", getDocumentById.get().getName());

        return BaseResponse.builder().code(HttpStatus.OK.value()).headers(headers).file(docFile).build();
    }

    @Override
    public BaseResponse viewFile(UUID id) {
        Optional<Document> getDocumentById = documentRepository.findById(id);
        if (getDocumentById.isEmpty()){
            return BaseResponse.builder().code(HttpStatus.UNPROCESSABLE_ENTITY.value()).file(null).build();
        }

        byte[] docFile = minioService.getFile(getDocumentById.get().getPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return BaseResponse.builder().code(HttpStatus.OK.value()).headers(headers).file(docFile).build();
    }
}
