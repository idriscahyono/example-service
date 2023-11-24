package idriscahyono.exampleservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idriscahyono.exampleservice.base.BaseService;
import idriscahyono.exampleservice.entity.User;
import idriscahyono.exampleservice.payload.request.CreateUserRequest;
import idriscahyono.exampleservice.payload.response.ProfileServiceResponse;
import idriscahyono.exampleservice.payload.response.UserDetailResponse;
import idriscahyono.exampleservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
public class UserController extends BaseService {

    @Autowired
    ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(
            path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public CreateUserRequest createUser(@RequestBody @Validated CreateUserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return request;
    }

    @GetMapping(path = "/users")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(path = "user/{username}")
    @ResponseBody
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable("username") @NotNull String username){
        Optional<User> user = userRepository.findFirstByUsername(username);

        ProfileServiceResponse profileServiceResponse = profileService.getRandomProfileUser();

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setId(user.get().getId());
        userDetailResponse.setUsername(user.get().getUsername());
        userDetailResponse.setName(user.get().getName());
        userDetailResponse.setEmail(user.get().getEmail());
        userDetailResponse.setPhone(user.get().getPhone());
        userDetailResponse.setProfile_url(profileServiceResponse.getDownload_url());
        userDetailResponse.setCreated_at(user.get().getCreated_at());

        return ResponseEntity.status(HttpStatus.OK).body(userDetailResponse);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") @NotNull String id){
        long pathId = Long.parseLong(id);

        userRepository.deleteById(pathId);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
