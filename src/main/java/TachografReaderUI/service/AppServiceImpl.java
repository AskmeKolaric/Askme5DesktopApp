package TachografReaderUI.service;

import TachografReaderUI.models.ErrorMessage;
import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.TokenResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AppServiceImpl implements AppService {

    private WebClient webClient;
    private final ObjectMapper objectMapper;
    private String loginUrl = "http://askme5.it/e5pro/api/user/singin";

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

}
