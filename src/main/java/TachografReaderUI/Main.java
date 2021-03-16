package TachografReaderUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.net.URL;


public class Main extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(TachographReaderApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            URL url = new File("src/main/resources/Main.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root, 800, 575);
            URL url2 = new File("src/main/resources/application.css").toURI().toURL();
            scene.getStylesheets().add(url2.toExternalForm());
            primaryStage.setTitle("E5-Čitač kartice za tahograf");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        this.applicationContext.close();
        Platform.exit();
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/
}
