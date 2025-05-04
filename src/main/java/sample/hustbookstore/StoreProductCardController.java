package sample.hustbookstore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ResourceBundle;
import java.net.URL;

public class StoreProductCardController implements Initializable {

    @FXML
    private Button productAdd;

    @FXML
    private AnchorPane productCard;

    @FXML
    private ImageView productImage;

    @FXML
    private TextArea productName;

    @FXML
    private TextField productPrice;

    @FXML
    private Spinner<?> productSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
