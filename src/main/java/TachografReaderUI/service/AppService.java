package TachografReaderUI.service;

import TachografReaderUI.models.LoginModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface AppService {

    String login(LoginModel loginModel) throws JsonProcessingException;

}
