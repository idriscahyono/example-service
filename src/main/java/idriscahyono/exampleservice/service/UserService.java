package idriscahyono.exampleservice.service;

import idriscahyono.exampleservice.base.BaseResponse;
import idriscahyono.exampleservice.entity.User;
import idriscahyono.exampleservice.payload.request.UserRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

public interface UserService {
    BaseResponse create(UserRequest request);
    BaseResponse findAll();
    BaseResponse getUserDetailByUsername(String username);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    BaseResponse uploadProfile(MultipartFile file, Long userId);
}