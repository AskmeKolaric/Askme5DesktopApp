package TachografReaderUI.service;

import TachografReaderUI.models.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Service
public class AppServiceImpl implements AppService {

    private WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String loginUrl = "http://askme5.it/e5pro/api/user/singin";
    private final String postUrl = "http://askme5.it/e5pro/api/ddd";

    private final ObjectProperty<User> user = new SimpleObjectProperty();

    private String token = "";
    private SuccessMessage msg;

    public AppServiceImpl() {
        this.objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public String login(LoginModel loginModel) throws JsonProcessingException {

        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Mono<TokenResponse> webAccessRegistration = webClient.method(HttpMethod.POST)
                .uri(loginUrl)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(loginModel)))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(ErrorMessage.class)
                                .flatMap(errorMessage ->
                                        Mono.error(new ResponseStatusException(clientResponse.statusCode(), "Va??a ??ifra nije ispravna!"))//Umjesto error moze nesto tipa -> Vasa sifra je  nije tacna itd...
                                )
                )
                .bodyToMono(TokenResponse.class);

        this.token = Objects.requireNonNull(webAccessRegistration.block()).getToken();

        return token;
    }

    @Override
    public SuccessMessage uploadFile(String name, String cardNumber, String date, File file, String token) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                fromFile(name, cardNumber, date, file),
                httpHeaders
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SuccessMessage> responseEntity = restTemplate.postForEntity(postUrl, requestEntity, SuccessMessage.class);

        return responseEntity.getBody();

    }

    private MultiValueMap<String, Object> fromFile(String name, String cardNumber, String date, File file) {

        MultiValueMap<String, Object> builder = new LinkedMultiValueMap<>();
        builder.add("ddd", new FileSystemResource(file));
        builder.add("name", name);
        builder.add("cardNumber", cardNumber);
        builder.add("date", date);
        return builder;
    }

}
