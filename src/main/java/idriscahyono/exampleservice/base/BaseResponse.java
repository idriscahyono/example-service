package idriscahyono.exampleservice.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BaseResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("error_validation")
    private Map<String, List<String>> errorValidation;

    @JsonProperty("data")
    private Object Data;
}
