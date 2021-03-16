package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AppService {

    TokenResponse login(LoginModel loginModel) throws JsonProcessingException;
    //other app functionality

}
