package sample.hustbookstore.controllers.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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


    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField specificAddressField;

    @FXML private ComboBox<Province> cboProvince;
    @FXML private ComboBox<District> cboDistrict;
    @FXML private ComboBox<Ward> cboWard;



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
            nameField.setText(localUser.getName());
            phoneNumberField.setText(localUser.getPhoneNumber());
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

    public void selectAddress(){
        Platform.runLater(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                File jsonFile = new File("src/main/resources/sample/hustbookstore/data/dvhcvn.json");

                JsonNode rootNode = objectMapper.readTree(jsonFile);
                JsonNode dataNode = rootNode.get("data");

                List<Province> provinces = objectMapper.convertValue(dataNode, new TypeReference<List<Province>>() {});

                ObservableList<Province> provinceList = FXCollections.observableArrayList(provinces);
                cboProvince.setItems(provinceList);

                cboProvince.setOnAction(event -> {
                    Province selectedProvince = cboProvince.getSelectionModel().getSelectedItem();
                    if (selectedProvince != null) {
                        ObservableList<District> districtList = FXCollections.observableArrayList(selectedProvince.getLevel2s());
                        cboDistrict.setItems(districtList);
                        cboWard.getItems().clear();
                    }
                });

                cboDistrict.setOnAction(event -> {
                    District selectedDistrict = cboDistrict.getSelectionModel().getSelectedItem();
                    if (selectedDistrict != null) {
                        ObservableList<Ward> wardList = FXCollections.observableArrayList(selectedDistrict.getLevel3s());
                        cboWard.setItems(wardList);
                    }
                });

//                cboWard.setOnAction(event -> {
//                    Ward selectedWard = cboWard.getSelectionModel().getSelectedItem();
//                    if(selectedWard != null){
//                        showAddress();
//                    }
//                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showAddress(){

    }

    public void initialize(){
        selectAddress();
        Cart.setCartUpdateListener(this);
        display();
    }
}
