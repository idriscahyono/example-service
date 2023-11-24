package idriscahyono.exampleservice.service;

import idriscahyono.exampleservice.component.RestClientComponent;
import idriscahyono.exampleservice.payload.response.ProfileServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    RestClientComponent restClientComponent;

    @Value("${profile-service.base-url}")
    private String profileBasUrl;

    @Override
    public ProfileServiceResponse getRandomProfileUser() {
        ProfileServiceResponse response = new ProfileServiceResponse();
        try{
            String url = profileBasUrl + "/id/" + createRandom() + "/info";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");

            response = restClientComponent.doExecute(HttpMethod.GET, url, null, null, ProfileServiceResponse.class, "");
            return response;
        }catch (Exception e){
            System.out.println("GET RANDOM PROFILE ERROR" + e);
            return response;
        }
    }

    private Integer createRandom(){
        Random rand = new Random();
        return rand.nextInt(100);
    }
}
