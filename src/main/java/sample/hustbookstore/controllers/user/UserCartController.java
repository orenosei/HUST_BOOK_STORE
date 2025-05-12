package sample.hustbookstore.controllers.user;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.hustbookstore.models.Cart;
import sample.hustbookstore.models.CartItem;

import static sample.hustbookstore.LaunchApplication.localCart;
import static sample.hustbookstore.LaunchApplication.localUser;

public class UserCartController {

    @FXML
    private Button applyVoucherBtn;

    @FXML
    private Text discountValue;

    @FXML
    private Text subTotalValue;

    @FXML
    private Text totalValue;

    @FXML
    private VBox vboxPane;

    @FXML
    private TextField voucherField;

    public void display(){
        ObservableList<CartItem> itemList = Cart.getCartItemList(localCart.getCartId());

        vboxPane.getChildren().clear();

        for(int i = 0; i < itemList.size(); i++){
            try{
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/sample/hustbookstore/user/user-cartItemCard-view.fxml"));
                AnchorPane pane = load.load();
                UserCartItemController itemC = load.getController();
                itemC.setData(itemList.get(i));

                vboxPane.getChildren().add(pane);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(){
        display();
    }



}
