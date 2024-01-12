package idriscahyono.exampleservice.service;

import idriscahyono.exampleservice.base.BaseResponse;
import idriscahyono.exampleservice.entity.Document;
import idriscahyono.exampleservice.entity.User;
import idriscahyono.exampleservice.entity.UserProfile;
import idriscahyono.exampleservice.payload.request.UserRequest;
import idriscahyono.exampleservice.payload.response.ProfileServiceResponse;
import idriscahyono.exampleservice.payload.response.UserDetailResponse;
import io.minio.ObjectWriteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static idriscahyono.exampleservice.constant.Storage.MINIO_STORAGE;

@Service
public class UserServiceImpl extends AppService implements UserService{
    @Override
    public BaseResponse create(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return BaseResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    @Override
    public BaseResponse findAll() {
        List<User> users = userRepository.findAll();

        return BaseResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .Data(users)
                        .build();
    }

    @Override
    public BaseResponse getUserDetailByUsername(String username) {
        String userProfileUrl;

        Optional<User> user = userRepository.findFirstByUsername(username);

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

        return BaseResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .Data(userDetailResponse)
                        .build();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public BaseResponse uploadProfile(MultipartFile file, Long userId){
        String originalFileName = file.getOriginalFilename();

        Optional<User> userFindById = userRepository.findById(userId);
        if (userFindById.isEmpty()){
            return BaseResponse.builder()
                            .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .message("user not found")
                            .build();
        }

        ObjectWriteResponse responseMinio = minioService.uploadFile(file, "user-profile");

        transactionOperations.executeWithoutResult(transactionStatus -> {
            Document document = new Document();
            document.setName(originalFileName);
            document.setPath(responseMinio.object());
            document.setStorage_type(MINIO_STORAGE);

            documentRepository.save(document);

            UserProfile userProfile = new UserProfile();
            userProfile.setUser_id(userId);
            userProfile.setDocument_id(document.getId());

            userProfileRepository.save(userProfile);
        });

        return BaseResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .build();
    }
}
