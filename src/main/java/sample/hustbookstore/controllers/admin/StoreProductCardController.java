package sample.hustbookstore.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.models.Product;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class StoreProductCardController implements Initializable {

    @FXML
    private AnchorPane productCard;

    @FXML
    private ImageView productImage;

    @FXML
    private TextArea productName;

    @FXML
    private TextField productImportPrice;

    @FXML
    private TextField productSellPrice;



    private Product prodData;
    private Image image;

    public void setData(Product prodData) {
        this.prodData = prodData;
        productName.setText(prodData.getName());
        productSellPrice.setText(String.valueOf(prodData.getSellPrice()));
        productImportPrice.setText(String.valueOf(prodData.getImportPrice()));

        String imagePath = prodData.getImage();
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                URL imageUrl = new URL(imagePath);
                image = new Image(imageUrl.toExternalForm(), 100, 160, true, true);
            } else {
                image = new Image(getClass().getResource("/sample/hustbookstore/img/notfound.jpg").toExternalForm(), 100, 160, true, true);
            }
        } catch (MalformedURLException e) {
            URL resourceUrl = getClass().getResource("/" + imagePath);
            if (resourceUrl != null) {
                image = new Image(resourceUrl.toExternalForm(), 100, 160, true, true);
            } else {
                image = new Image(getClass().getResource("/sample/hustbookstore/img/notfound.jpg").toExternalForm(), 100, 160, true, true);
            }
        }

        productImage.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
