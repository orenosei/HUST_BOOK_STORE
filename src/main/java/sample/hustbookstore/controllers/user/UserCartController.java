package sample.hustbookstore.controllers.user;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.hustbookstore.models.Cart;
import sample.hustbookstore.models.CartItem;
import sample.hustbookstore.models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static sample.hustbookstore.LaunchApplication.*;

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

    private Alert alert;

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

    private Connection connect = database.connectDB();

    String voucherCode;

    public void pressVoucherBtn(){
        if(voucherField.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a voucher code!");
            alert.showAndWait();
        }else{
            voucherCode = voucherField.getText();

            if (localVoucher.isVoucherExists(voucherCode)) {
                float percent;
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to apply this voucher?\nPlease note that only one voucher can be applied to an order!");

                Optional<ButtonType> confirm = alert.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    percent = (localVoucher.getVoucher(voucherCode).getDiscount())/100f;
                    discountValue.setText(Float.toString(localCart.calculateTotalPrice(localCart.getCartId())*percent));
                    totalValue.setText(Float.toString(localCart.calculateTotalPrice(localCart.getCartId())*(1-percent)));
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Voucher does not exist!");
                alert.showAndWait();
            }
        }
    }
}
