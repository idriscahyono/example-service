package idriscahyono.exampleservice.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResourcesTest {

    @Value("${profile-service.base-url}")
    private String profileBasUrl;

    @Test
    void getApplicationPropertiesTest(){
        System.out.println(profileBasUrl);
    }
}
