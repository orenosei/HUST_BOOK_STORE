package sample.hustbookstore.controllers.user;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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

public class UserCartController implements CartUpdateListener{

    @Override
    public void onCartUpdated() {
        Platform.runLater(this::display); // Cập nhật UI trên luồng JavaFX
    }

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


public void display() {
    Task<ObservableList<CartItem>> loadItemsTask = new Task<>() {
        @Override
        protected ObservableList<CartItem> call() throws Exception {
            return localCart.getCartItemList(localCart.getCartId());
        }
    };

    loadItemsTask.setOnSucceeded(event -> {
        ObservableList<CartItem> itemList = loadItemsTask.getValue();
        vboxPane.getChildren().clear();

        for (int i = 0; i < itemList.size(); i++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/sample/hustbookstore/user/user-cartItemCard-view.fxml"));
                AnchorPane pane = load.load();
                UserCartItemController itemC = load.getController();

                itemC.setUserCartController(this);
                itemC.setData(itemList.get(i));
                vboxPane.getChildren().add(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showSubTotalValue();
    });

    loadItemsTask.setOnFailed(event -> {
        Throwable error = loadItemsTask.getException();
        error.printStackTrace();
    });

    Thread thread = new Thread(loadItemsTask);
    thread.setDaemon(true);
    thread.start();
}


    public void showSubTotalValue(){
        subTotalValue.setText(Float.toString(localCart.calculateTotalPrice(localCart.getCartId())));
    }

    public void initialize(){
        Cart.setCartUpdateListener(this);
        display();
    }

}
