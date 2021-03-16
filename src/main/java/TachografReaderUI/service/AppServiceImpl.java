package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.TokenResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class AppServiceImpl implements AppService{

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String loginUrl = "http://askme5.it/e5pro/api/user/singin";

    public AppServiceImpl(){
        this.webClient =  WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public TokenResponse login(LoginModel loginModel) throws JsonProcessingException {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Mono<TokenResponse> webAccessRegistration = webClient.method(HttpMethod.POST)
                .uri(loginUrl)
                .body(BodyInserters.fromObject(objectMapper.writeValueAsString(loginModel)))
                .retrieve().bodyToMono(TokenResponse.class);

        TokenResponse tokenResponse = webAccessRegistration.block();

        return tokenResponse;
    }
}
