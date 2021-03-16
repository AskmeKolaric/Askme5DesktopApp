package TachografReaderUI.controller;

import TachografReaderUI.models.LoginModel;
import TachografReaderUI.models.TokenResponse;
import TachografReaderUI.service.AppService;
import TachografReaderUI.service.AppServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import TachografReaderUI.User;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.*;

@Controller
public class LoginController {

    private final AppService appService = new AppServiceImpl();

    private final ObjectProperty<User> user = new SimpleObjectProperty<>();

    public final ObjectProperty<User> userProperty() {
        return this.user;
    }

    public final User getUser() {
        return this.userProperty().get();
    }

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void ok() throws JsonProcessingException {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        if (authenticate(userName, password)) {
            setUser(new User(userName));
            LoginModel loginModel = new LoginModel("maxknn@gmail.com", "767601");//For testing, should be taken frow the textbox
            appService.login(loginModel);//perform login
            errorLabel.setText("");
        } else {
            errorLabel.setText("Incorrect login details");
        }
        clearFields();
    }


    @FXML
    private void cancel() {
        setUser(null);
        clearFields();
        errorLabel.setText("");
    }

    private boolean authenticate(String userName, String password) {
        // in real life, do real authentication...
        if (userName.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }

    private void clearFields() {
        userNameField.setText("");
        passwordField.setText("");
    }

    public final void setUser(final User user) {
        this.userProperty().set(user);
    }
}
