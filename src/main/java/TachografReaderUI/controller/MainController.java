package TachografReaderUI.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    public BorderPane root;

    @FXML
    private Parent login;

    private Parent mainView;

    @FXML
    private LoginController loginController;

    public void initialize() {
        loginController.userProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser == null) {
                root.setCenter(login);
            } else {
                if (mainView == null) {
                    loadMainView();
                }
                root.setCenter(mainView);
            }
            root.getScene().getWindow().sizeToScene();
        });
        loginController.clearAllFields();
    }

    private void loadMainView() {
        try {
            URL urlDataView = new File("src/main/java/TachografReaderUi/DataView.fxml").toURI().toURL();
            URL url = getClass().getResource("/DataView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            mainView = loader.load();
            DataViewController controller = loader.getController();
            controller.userProperty().bindBidirectional(
                    loginController.userProperty());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
