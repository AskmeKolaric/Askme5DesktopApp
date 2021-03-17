package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface AppService {

    TokenResponse login(LoginModel loginModel) throws JsonProcessingException;
    //other app functionality

}
