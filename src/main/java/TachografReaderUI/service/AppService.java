package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.SuccessMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface AppService {

    String login(LoginModel loginModel) throws JsonProcessingException;
    SuccessMessage uploadFile(String name, String cardNumber, String date, File file, String token) throws JsonProcessingException;

}
