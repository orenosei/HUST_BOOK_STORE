package sample.hustbookstore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Login_demo_Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}