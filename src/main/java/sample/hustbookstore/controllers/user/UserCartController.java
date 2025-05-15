package sample.hustbookstore.controllers.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.hustbookstore.models.Cart;
import sample.hustbookstore.models.CartItem;
import sample.hustbookstore.models.address.District;
import sample.hustbookstore.models.address.Province;
import sample.hustbookstore.models.address.Ward;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.util.List;

import static sample.hustbookstore.LaunchApplication.localCart;


import java.util.Optional;

import static sample.hustbookstore.LaunchApplication.*;

public class UserCartController implements CartUpdateListener{

    @Override
    public void onCartUpdated() {
        Platform.runLater(this::display);
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

    @FXML private ComboBox<Province> cboProvince;
    @FXML private ComboBox<District> cboDistrict;
    @FXML private ComboBox<Ward> cboWard;


    private Alert alert;

    private float percent = 0;

    public void showSubTotalValue(){
        subTotalValue.setText(Float.toString(localCart.calculateTotalPrice(localCart.getCartId())));
        totalValue.setText(Float.toString(localCart.calculateTotalPrice(localCart.getCartId())*(1-percent)));
    }

    public void display() {
        try {
            ObservableList<CartItem> itemList = localCart.getCartItemList(localCart.getCartId());
            vboxPane.getChildren().clear();

            for (CartItem cartItem : itemList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/user/user-cartItemCard-view.fxml"));
                AnchorPane pane = loader.load();
                UserCartItemController itemController = loader.getController();

                itemController.setUserCartController(this);
                itemController.setData(cartItem);
                vboxPane.getChildren().add(pane);
            }

            showSubTotalValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAddress() {
        Task<Void> addressTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                File jsonFile = new File("src/main/resources/sample/hustbookstore/data/dvhcvn.json");
                JsonNode rootNode = objectMapper.readTree(jsonFile);
                JsonNode dataNode = rootNode.get("data");

                List<Province> provinces = objectMapper.convertValue(dataNode, new TypeReference<>() {});

                Platform.runLater(() -> {
                    ObservableList<Province> provinceList = FXCollections.observableArrayList(provinces);
                    cboProvince.setItems(provinceList);

                    cboProvince.setOnAction(event -> handleProvinceSelection());
                    cboDistrict.setOnAction(event -> handleDistrictSelection());
                    cboWard.setOnAction(event -> showAddress());
                });

                return null;
            }
        };

        addressTask.setOnFailed(event -> {
            Throwable e = addressTask.getException();
            e.printStackTrace();
        });

        new Thread(addressTask).start();
    }

    private void handleProvinceSelection() {
        Province selectedProvince = cboProvince.getSelectionModel().getSelectedItem();
        if (selectedProvince != null) {
            ObservableList<District> districtList = FXCollections.observableArrayList(selectedProvince.getLevel2s());
            cboDistrict.setItems(districtList);
            cboWard.getItems().clear();
        }
    }

    private void handleDistrictSelection() {
        District selectedDistrict = cboDistrict.getSelectionModel().getSelectedItem();
        if (selectedDistrict != null) {
            ObservableList<Ward> wardList = FXCollections.observableArrayList(selectedDistrict.getLevel3s());
            cboWard.setItems(wardList);
        }
    }

    public void showAddress(){

    }



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


    public void initialize(){
        selectAddress();
        Cart.setCartUpdateListener(this);
        Platform.runLater(this::display);
    }
}
