package sample.hustbookstore.controllers.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import sample.hustbookstore.models.*;
import sample.hustbookstore.models.address.District;
import sample.hustbookstore.models.address.Province;
import sample.hustbookstore.models.address.Ward;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.time.LocalDate;
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

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField specificAddressField;


    @FXML
    private Button placeOrderBtn;

    @FXML private ComboBox<Province> cboProvince;
    @FXML private ComboBox<District> cboDistrict;
    @FXML private ComboBox<Ward> cboWard;

    @FXML
    private AnchorPane cartPane;

    @FXML
    private Button billOkBtn;

    @FXML
    private AnchorPane billPane;

    @FXML
    private TextField billReceiverAddress;

    @FXML
    private TextField billReceiverName;

    @FXML
    private TextField billReceiverPhoneNumber;

    @FXML
    private Text discountValueInBill;

    @FXML
    private TableView<CartItem> orderListTable;
    @FXML
    private TableColumn<CartItem, String> prodNameCol;
    @FXML
    private TableColumn<CartItem, Number> prodQtyCol;
    @FXML
    private TableColumn<CartItem, String> prodPriceCol;


    @FXML
    private Text subTotalValueInBill;

    @FXML
    private Text totalValueInBill;


    private Alert alert;

    private float percent = 0;

    private BillList billList = new BillList();

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

    public void pressVoucherBtn() {
        if (voucherField.getText().isEmpty()) {
            showErrorAlert("Please enter a voucher code!");
            return;
        }

        voucherCode = voucherField.getText();
        if (!localVoucher.isVoucherExists(voucherCode)) {
            showErrorAlert("Voucher does not exist!");
            return;
        }
        Voucher voucher = localVoucher.getVoucher(voucherCode);
        if (voucher.getRemaining() <= 0) {
            showErrorAlert("This voucher has been used up!");
            return;
        }
        if (voucher.getDuration().isBefore(LocalDate.now())) {
            showErrorAlert("This voucher has expired!");
            return;
        }
        showConfirmationDialog(voucher);
    }

    private void showConfirmationDialog(Voucher voucher) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION MESSAGE");
        alert.setHeaderText(null);
        alert.setContentText("Apply this voucher? (Only one voucher per order)");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            float total = localCart.calculateTotalPrice(localCart.getCartId());
            percent = voucher.getDiscount() / 100f;

            discountValue.setText(String.format("%.2f", total * percent));
            totalValue.setText(String.format("%.2f", total * (1 - percent)));
        }
    }

    private void showErrorAlert(String message) {
//        new Alert(Alert.AlertType.ERROR, message).showAndWait();
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong =((");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleOrderBtn(){
        // List<CartItems> getSelectedCartItems()
        List<CartItem> selectedItems = localCart.getSelectedCartItems(localCart.getCartId());

        // check stock = 0 item => user phai bỏ select
        for(CartItem item: selectedItems){
            if(item.getProduct().getStock() == 0){
                showErrorAlert(item.getProduct().getName() + " is out of stock!");
                return;
            }
        }

        // In bill lên màn hình để câu giờ cho các hành động tiếp theo:
        cartPane.setOpacity(0.2);
        billPane.setVisible(true);
        updateBillDetails(selectedItems);


        Task<Void> backgroundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                voucherCode = voucherField.getText();

//                float usedVoucher = voucherCode == null ? 0 : localVoucher.getVoucher(voucherCode).getDiscount();

                float usedVoucher = 0;
                if (voucherCode != null && !voucherCode.isEmpty()) {
                    Voucher voucher = localVoucher.getVoucher(voucherCode);
                    if (voucher != null) {
                        usedVoucher = voucher.getDiscount();
                    }
                }

                Bill newBill = billList.prepareBill(localUser.getUserId(), selectedItems, usedVoucher);
                billList.addBill(newBill);

                localInventory.updateProductStock(selectedItems);

                if (voucherCode != null) {
                    localVoucher.updateVoucherRemaining(voucherCode);
                }


                Platform.runLater(() -> {
                    for (CartItem item : selectedItems) {
                        localCart.deleteCartItem(item);
                    }
                    display();
                });

                return null;
            }
        };

        backgroundTask.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                if (userHomeScreenController != null) {
                    userHomeScreenController.reloadStore();
                }
            });
        });

        backgroundTask.setOnFailed(e -> {
            Throwable exception = backgroundTask.getException();
            Platform.runLater(() -> {
                showErrorAlert("Error processing order: " + exception.getMessage());
                exception.printStackTrace();
            });
        });

        new Thread(backgroundTask).start();

    }

    private void updateBillDetails(List<CartItem> selectedItems) {

        billReceiverName.setText(nameField.getText());
        billReceiverPhoneNumber.setText(phoneNumberField.getText());

        String address = String.format("%s, %s, %s, %s",
                specificAddressField.getText(),
                cboWard.getSelectionModel().getSelectedItem().getName(),
                cboDistrict.getSelectionModel().getSelectedItem().getName(),
                cboProvince.getSelectionModel().getSelectedItem().getName()
        );
        billReceiverAddress.setText(address);


        discountValueInBill.setText(discountValue.getText());
        subTotalValueInBill.setText(subTotalValue.getText());
        totalValueInBill.setText(totalValue.getText());


        setupOrderTable(selectedItems);
    }

    private void setupOrderTable(List<CartItem> selectedItems) {
        ObservableList<CartItem> items = FXCollections.observableArrayList(selectedItems);

        prodNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        prodQtyCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()));
        prodPriceCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.format("%,.0fđ", cellData.getValue().getProduct().getSellPrice() * cellData.getValue().getQuantity())
        ));

        orderListTable.setItems(items);
    }

    @FXML
    private void handleBillOk() {
        billPane.setVisible(false);
        cartPane.setOpacity(1);
    }




    private UserHomeScreenController userHomeScreenController;

    public void setHomeScreenController(UserHomeScreenController userHomeScreenController) {
        this.userHomeScreenController = userHomeScreenController;
    }


    public void initialize(){
        selectAddress();
        Cart.setCartUpdateListener(this);
        //Platform.runLater(this::display);
        display();

        billPane.setVisible(false);

        orderListTable.setPlaceholder(new Label("Không có sản phẩm nào"));
    }
}
