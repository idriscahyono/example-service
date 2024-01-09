package idriscahyono.exampleservice.service;

import idriscahyono.exampleservice.base.BaseResponse;
import java.util.UUID;

public interface DocumentService {
   BaseResponse findById(Long id);
   BaseResponse getFile(UUID id);
   BaseResponse viewFile(UUID id);
}