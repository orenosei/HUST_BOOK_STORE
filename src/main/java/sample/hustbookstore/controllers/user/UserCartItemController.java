package sample.hustbookstore.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.hustbookstore.models.Cart;
import sample.hustbookstore.models.CartItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static sample.hustbookstore.LaunchApplication.localCart;

public class UserCartItemController {

    @FXML
    private CheckBox itemCheckBox;

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
        itemCheckBox.setSelected(item.isSelected());


        itemSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0,
                item.getProduct().getStock(),
                Math.min(item.getProduct().getStock(), item.getQuantity())
        ));

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

    @FXML
    void handleDeleteButton(ActionEvent event) {
        if(event.getSource() == itemDelete) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this item?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {
                localCart.deleteCartItem(item);
                reload();
            }
        }
    }


    @FXML
    void handleSelectItem(ActionEvent event) {
        if(event.getSource() == itemCheckBox) {
            item.setSelected(itemCheckBox.isSelected());
            localCart.updateCartItem(item);

            if (userCartController != null) {
                userCartController.showSubTotalValue();
            }
        }
    }

    @FXML
    void handleSpinnerChange(int newQuantity){
        item.setQuantity(newQuantity);
        localCart.updateCartItem(item);

        if (userCartController != null) {
            userCartController.showSubTotalValue();
        }
    }

    private UserCartController userCartController;

    public void setUserCartController(UserCartController userCartController) {
        this.userCartController = userCartController;
    }
    private void reload(){
        if (userCartController != null) {
            userCartController.display();
            userCartController.showSubTotalValue();
        }
    }

    public void initialize() {
        if (userCartController != null) {
            userCartController.showSubTotalValue();
        }
        itemSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {handleSpinnerChange(newValue);});
    }

}

