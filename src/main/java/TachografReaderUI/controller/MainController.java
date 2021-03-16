package TachografReaderUI.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
                root.getScene().getWindow().sizeToScene();
            } else {
                if (mainView == null) {
                    loadMainView();
                }
                root.setCenter(mainView);
                root.getScene().getWindow().sizeToScene();
            }
        });
    }

    private void loadMainView() {
        try {
            URL urlDataView = new File("src/main/resources/DataView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(urlDataView);
            mainView = loader.load();
            DataViewController controller = loader.getController();
            controller.userProperty().bindBidirectional(
                    loginController.userProperty());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
