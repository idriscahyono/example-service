package idriscahyono.exampleservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotEmpty(message = "is required")
    private String username;

    @NotEmpty(message = "is required")
    private String name;

    @NotEmpty(message = "is required")
    private String email;

    @NotEmpty(message = "is required")
    private String phone;
}
