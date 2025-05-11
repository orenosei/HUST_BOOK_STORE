package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Stationery;
import sample.hustbookstore.models.Toy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserStoreProductCardController implements Initializable {

    @FXML
    private AnchorPane productCard;

    @FXML
    private ImageView productImage;

    @FXML
    private TextArea productName;

    @FXML
    private TextField productSellPrice;

    @FXML
    private Button addToCart_btn;

    @FXML
    private Spinner<Integer> productSpinner;



    private Book bookData;
    private Stationery stationeryData;
    private Toy toyData;
    private Image image;

    private SpinnerValueFactory<Integer> spin;

    public void setBookData(Book bookData) {
        this.bookData = bookData;
        productName.setText(bookData.getName());
        productSellPrice.setText(String.valueOf(bookData.getSellPrice()));

        setQuantity(bookData.getStock());

        String imagePath = bookData.getImage();
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

    public void setStationeryData(Stationery stationeryData) {
        this.stationeryData = stationeryData;
        productName.setText(stationeryData.getName());
        productSellPrice.setText(String.valueOf(stationeryData.getSellPrice()));

        setQuantity(stationeryData.getStock());

        String imagePath = stationeryData.getImage();
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

    public void setToyData(Toy toyData) {
        this.toyData = toyData;
        productName.setText(toyData.getName());
        productSellPrice.setText(String.valueOf(toyData.getSellPrice()));

        setQuantity(toyData.getStock());

        String imagePath = toyData.getImage();
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

    public void setQuantity(int qty) {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, qty, 0);
        productSpinner.setValueFactory(spin);
    }

    //public void addButton() {
//
   //  }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
