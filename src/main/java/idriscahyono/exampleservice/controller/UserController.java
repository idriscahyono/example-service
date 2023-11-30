package idriscahyono.exampleservice.controller;

import idriscahyono.exampleservice.base.BaseResponse;
import idriscahyono.exampleservice.base.BaseService;
import idriscahyono.exampleservice.entity.Document;
import idriscahyono.exampleservice.entity.User;
import idriscahyono.exampleservice.entity.UserProfile;
import idriscahyono.exampleservice.payload.request.CreateUserRequest;
import idriscahyono.exampleservice.payload.response.ProfileServiceResponse;
import idriscahyono.exampleservice.payload.response.UserDetailResponse;
import io.minio.ObjectWriteResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static idriscahyono.exampleservice.constant.Storage.MINIO_STORAGE;

@Controller
@RequestMapping("/api/v1")
public class UserController extends BaseService {
    @PostMapping(
            path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<BaseResponse> createUser(@RequestBody @Validated CreateUserRequest request){
        Optional<User> findUserByUserName = userRepository.findFirstByUsername(request.getUsername());
        if (findUserByUserName.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    BaseResponse.builder()
                            .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .message("Username already exist")
                            .build()
            );
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .build()
        );
    }

    @GetMapping(path = "/users")
    @ResponseBody
    public ResponseEntity<BaseResponse> getUsers(){
        List<User> users = userRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .Data(users)
                        .build()
        );
    }

    @GetMapping(path = "user/{username}")
    @ResponseBody
    public ResponseEntity<BaseResponse> getUserDetail(@PathVariable("username") @NotNull String username){
        String userProfileUrl;

        Optional<User> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            return ResponseEntity.ok().body(
                    BaseResponse.builder()
                            .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .message("Username not found")
                            .build()
            );
        }

        if (user.get().getUserProfile() != null){
            userProfileUrl = serviceBaseUrl + "/api/v1/doc/view/" + user.get().getUserProfile().getDocument_id();
        } else {
            ProfileServiceResponse profileServiceResponse = profileService.getRandomProfileUser();
            userProfileUrl = profileServiceResponse.getDownload_url();
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setId(user.get().getId());
        userDetailResponse.setUsername(user.get().getUsername());
        userDetailResponse.setName(user.get().getName());
        userDetailResponse.setEmail(user.get().getEmail());
        userDetailResponse.setPhone(user.get().getPhone());
        userDetailResponse.setProfile_url(userProfileUrl);
        userDetailResponse.setCreated_at(user.get().getCreated_at());

        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .Data(userDetailResponse)
                        .build()
        );
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") @NotNull Long id){
        userRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "/user/profile")
    @ResponseBody
    public ResponseEntity<BaseResponse> uploadProfile(@RequestParam("file") MultipartFile file, @RequestParam("user_id") @NotNull Long user_id){
        String originalFileName = file.getOriginalFilename();

        Optional<User> userFindById = userRepository.findById(user_id);
        if (userFindById.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    BaseResponse.builder()
                            .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .message("user not found")
                            .build()
            );
        }

        ObjectWriteResponse responseMinio = minioService.uploadFile(file, "user-profile");

        transactionOperations.executeWithoutResult(transactionStatus -> {
            Document document = new Document();
            document.setName(originalFileName);
            document.setPath(responseMinio.object());
            document.setStorage_type(MINIO_STORAGE);

            documentRepository.save(document);

            UserProfile userProfile = new UserProfile();
            userProfile.setUser_id(user_id);
            userProfile.setDocument_id(document.getId());

            userProfileRepository.save(userProfile);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
                BaseResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .build()
        );
    }

    @GetMapping(path = "/doc/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") @NotNull UUID id){
        Optional<Document> getDocumentById = documentRepository.findById(id);
        if (getDocumentById.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }

        byte[] docFile = minioService.getFile(getDocumentById.get().getPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", getDocumentById.get().getName());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(docFile);
    }

    @GetMapping(path = "/doc/view/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("id") @NotNull UUID id){
        Optional<Document> getDocumentById = documentRepository.findById(id);
        if (getDocumentById.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }

        byte[] docFile = minioService.getFile(getDocumentById.get().getPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(docFile);
    }
}
