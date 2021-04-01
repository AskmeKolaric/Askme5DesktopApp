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
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import okhttp3.MultipartBody;

import java.util.Objects;

@Service
public class AppServiceImpl implements AppService {

    private WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String loginUrl = "http://askme5.it/e5pro/api/user/singin";
    private final String postUrl = "http://askme5.it/e5pro/api/ddd";

    private final ObjectProperty<User> user = new SimpleObjectProperty();

    private String token = "";

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
                                        Mono.error(new ResponseStatusException(clientResponse.statusCode(), "Vaša šifra nije ispravna!"))//Umjesto error moze nesto tipa -> Vasa sifra je  nije tacna itd...
                                )
                )
                .bodyToMono(TokenResponse.class);

        this.token = Objects.requireNonNull(webAccessRegistration.block()).getToken();

        return token;
    }

    @Override
    public void uploadFile(RequestBody name, RequestBody cardNumber, RequestBody date, MultipartBody.Part file, String token) throws JsonProcessingException {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        Mono<ResponseBody> successMessageMono = webClient.method(HttpMethod.POST)
                .uri(postUrl)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromFile(name, cardNumber, date, file)))
                .retrieve()
                .bodyToMono(ResponseBody.class);

        //LOGGING
        System.out.println(Objects.requireNonNull(successMessageMono));

    }

    private MultiValueMap<String, HttpEntity<?>> fromFile(RequestBody name, RequestBody cardNumber, RequestBody date, MultipartBody.Part file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("ddd", file);
        builder.part("name", name);
        builder.part("date", date);
        builder.part("cardNumber", cardNumber);
        return builder.build();
    }

}
