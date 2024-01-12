package idriscahyono.exampleservice.payload.request;

import idriscahyono.exampleservice.application.annotations.EmailExist;
import idriscahyono.exampleservice.application.annotations.PhoneExist;
import idriscahyono.exampleservice.application.annotations.UsernameExist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotEmpty(message = "is required")
    @UsernameExist
    private String username;

    @NotEmpty(message = "is required")
    private String name;

    @NotEmpty(message = "is required")
    @EmailExist
    private String email;

    @NotEmpty(message = "is required")
    @PhoneExist
    private String phone;
}
