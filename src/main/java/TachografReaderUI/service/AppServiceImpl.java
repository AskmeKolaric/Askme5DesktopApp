package TachografReaderUI.service;

import TachografReaderUI.models.ErrorMessage;
import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.SuccessMessage;
import TachografReaderUI.models.TokenResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AppServiceImpl implements AppService {

    private WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String loginUrl = "http://askme5.it/e5pro/api/user/singin";
    private final String postUrl = "http://askme5.it/e5pro/api/ddd";

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

        return Objects.requireNonNull(webAccessRegistration.block()).getToken();
    }

    @Override
    public void uploadFile(String name, String cardNumber, String date, File file) throws JsonProcessingException {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        Mono<SuccessMessage> successMessageMono = webClient.post()
                .uri(postUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromFile(name, cardNumber, date, file)))
                .retrieve()
                .bodyToMono(SuccessMessage.class);

        //LOGGING
        System.out.println(Objects.requireNonNull(successMessageMono.block()).getMessage());
        System.out.println(Objects.requireNonNull(successMessageMono.block()).getPoruka());

    }

    private MultiValueMap<String, HttpEntity<?>> fromFile(String name, String cardNumber, String date, File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("ddd", new FileSystemResource(file));
        builder.part("name", name);
        builder.part("date", date);
        builder.part("cardNumber", cardNumber);
        return builder.build();
    }

}
