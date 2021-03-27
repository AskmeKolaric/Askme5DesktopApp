package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface AppService {

    String login(LoginModel loginModel) throws JsonProcessingException;
    void uploadFile(String name, String cardNumber, String date,File file) throws JsonProcessingException;

}
