package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.hustbookstore.models.CartItem;

import java.net.MalformedURLException;
import java.net.URL;

public class UserCartItemController {

    @FXML
    private CheckBox itemCheck;

    @FXML
    private Button itemDelete;

    @FXML
    private ImageView itemImage;

    @FXML
    private TextArea itemName;

    @FXML
    private TextField itemPrice;

    @FXML
    private Spinner<Integer> itemSpinner;

    private CartItem item;
    private Image image;

    public void setData(CartItem item) {
        this.item = item;
        itemName.setText(item.getProduct().getName());
        itemPrice.setText(String.valueOf(item.getProduct().getSellPrice()));
        itemCheck.setSelected(item.isSelected());
        itemSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, item.getProduct().getStock(), item.getQuantity()));

        String imagePath = item.getProduct().getImage();
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

        itemImage.setImage(image);
    }

}