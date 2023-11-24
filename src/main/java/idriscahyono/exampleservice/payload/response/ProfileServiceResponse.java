package idriscahyono.exampleservice.payload.response;

import lombok.Data;

@Data
public class ProfileServiceResponse {
    private int id;

    private String author;

    private String width;

    private String height;

    private String url;

    private String download_url;
}
