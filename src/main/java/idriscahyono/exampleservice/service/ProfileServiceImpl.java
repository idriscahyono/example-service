package idriscahyono.exampleservice.service;

import idriscahyono.exampleservice.component.RestClientComponent;
import idriscahyono.exampleservice.payload.response.ProfileServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    RestClientComponent restClientComponent;

    @Value("${profile-service.base-url}")
    private String profileBaseUrl;

    @Override
    public ProfileServiceResponse getRandomProfileUser() {
        ProfileServiceResponse response = new ProfileServiceResponse();
        try{
            String url = profileBaseUrl + "/id/" + createRandom() + "/info";

//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//            headers.set("Accept", "application/json");

            response = restClientComponent.doExecute(HttpMethod.GET, url, null, null, ProfileServiceResponse.class, "");
            return response;
        }catch (Exception e){
            log.error("get random profle got error {}",e.getMessage(), e);
            return response;
        }
    }

    private Integer createRandom(){
        Random rand = new Random();
        return rand.nextInt(100);
    }
}
