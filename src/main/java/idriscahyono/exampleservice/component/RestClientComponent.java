package idriscahyono.exampleservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RestClientComponent {

    @Autowired
    private RestTemplate restTemplate;

    @Value("5s")
    private Duration readTimeout;

    public RestClientComponent(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder
                .setReadTimeout(readTimeout)
                .build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }

    public <T> T doExecute(HttpMethod method, String url, Object request, HttpEntity reqEntity,
                           Class<T> responseType,
                           Object... uriVariables){
        T result =  null;
        switch (method){
            case GET:
                result = restTemplate.getForObject(url, responseType, uriVariables);
                break;
            case POST:
                result = restTemplate.postForEntity(url, request, responseType, uriVariables).getBody();
                break;
            case PUT:
                result = restTemplate.exchange(url, method, reqEntity, responseType).getBody();
                break;
            case DELETE:
                restTemplate.delete(url, uriVariables);
                break;
            default:
                throw new UnsupportedOperationException(String.format("unsupported http method(method=%s)", method));
        }
        return result;
    }

}
