package sample.hustbookstore;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;


public class LaunchApplication extends Application {

    @FXML
    private Button admin_btn;

    @FXML
    private StackPane welcomeScreen;

    @FXML
    private Button user_btn;

    public void switchAdminLogin() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/login-view.fxml"));
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = (Stage) welcomeScreen.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchUserLogin() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/user-login-view.fxml"));
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = (Stage) welcomeScreen.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LaunchApplication.class.getResource("LaunchApplication.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 720);
        stage.setTitle("Welcome to HUST Book Store");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}