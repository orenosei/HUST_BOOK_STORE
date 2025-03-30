package sample.hustbookstore;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane home_screen;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Button store_btn;

    @FXML
    private Text username;

    public void displayUsername(){
        // đợi 2 anh chị kia làm xong login để lấy username
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
