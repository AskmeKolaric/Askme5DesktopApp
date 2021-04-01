package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

@Service
public interface AppService {

    String login(LoginModel loginModel) throws JsonProcessingException;
    void uploadFile(RequestBody name, RequestBody cardNumber, RequestBody date, MultipartBody.Part file, String token) throws JsonProcessingException;

}
