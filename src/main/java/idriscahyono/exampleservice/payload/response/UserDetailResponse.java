package idriscahyono.exampleservice.payload.response;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.Instant;

@Data
public class UserDetailResponse {
    private Long id;

    private String username;

    private String name;

    private String email;

    private String phone;

    private String profile_url;

    private Instant created_at;
}
