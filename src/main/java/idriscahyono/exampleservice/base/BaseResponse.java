package idriscahyono.exampleservice.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class BaseResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errorValidation;

    @JsonProperty("data")
    private Object Data;

    @JsonProperty("headers")
    private HttpHeaders headers;

    private byte[] file;
}
