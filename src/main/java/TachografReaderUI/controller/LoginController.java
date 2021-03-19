package TachografReaderUI.controller;

import TachografReaderUI.models.LoginModel;
import TachografReaderUI.service.AppService;
import TachografReaderUI.service.AppServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import TachografReaderUI.models.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {

    private final AppService appService = new AppServiceImpl();

    private final ObjectProperty<User> user = new SimpleObjectProperty<>();

    public final ObjectProperty<User> userProperty() {
        return this.user;
    }

    private static String email = "maxknn@gmail.com";//TODO move to application.properties

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void ok() throws JsonProcessingException {

        LoginModel loginModel = new LoginModel(email, passwordField.getText());//For testing,use default value for testing
        try {
            String tokenResponse = appService.login(loginModel);
            System.out.println("TOKEN: " + tokenResponse);//perform login
            setUser(new User(userNameField.getText()));
            errorLabel.setText("");
            //TODO check tokens!
        } catch (ResponseStatusException e) {
            //catch if there is error by login and set error message
            setUser(null);
            System.out.println(e.getMessage());
            errorLabel.setText(e.getMessage());
        }
        clearAllFields();
    }

    @FXML
    private void cancel() {
        clearAllFields();
        errorLabel.setText("");
    }

    public void clearAllFields(){
        userNameField.setText("");
        passwordField.setText("");
    }

    public final void setUser(final User user) {
        this.userProperty().set(user);
    }

}
