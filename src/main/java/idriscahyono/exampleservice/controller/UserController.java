package idriscahyono.exampleservice.controller;

import idriscahyono.exampleservice.application.annotations.UsernameValid;
import idriscahyono.exampleservice.base.BaseResponse;
import idriscahyono.exampleservice.payload.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/api/v1/user")
@Validated
public class UserController extends AppController {
    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@RequestBody @Validated UserRequest request){
        BaseResponse result = userService.create(request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<BaseResponse> getUsers(){
        BaseResponse result = userService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<BaseResponse> getUserDetail(@PathVariable("username") @NotNull @UsernameValid String username){
        BaseResponse result = userService.getUserDetailByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") @NotNull Long id){
       userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "/profile")
    @ResponseBody
    public ResponseEntity<BaseResponse> uploadProfile(@RequestParam("file") MultipartFile file, @RequestParam("user_id") @NotNull Long userId){
        BaseResponse result = userService.uploadProfile(file, userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
