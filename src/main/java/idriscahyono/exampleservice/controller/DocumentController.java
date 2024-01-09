package idriscahyono.exampleservice.controller;

import idriscahyono.exampleservice.base.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/doc")
public class DocumentController extends AppController {

    @GetMapping(path = "/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") @NotNull UUID id){
        BaseResponse result = documentService.getFile(id);

        return ResponseEntity.status(result.getCode()).headers(result.getHeaders()).body(result.getFile());
    }

    @GetMapping(path = "/view/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("id") @NotNull UUID id){
        BaseResponse result = documentService.viewFile(id);

        return ResponseEntity.status(result.getCode()).headers(result.getHeaders()).body(result.getFile());
    }
}
