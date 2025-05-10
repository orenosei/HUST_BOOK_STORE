package sample.hustbookstore.controllers.admin;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import sample.hustbookstore.models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;


import static sample.hustbookstore.LaunchApplication.*;

public class VoucherController {
    @FXML
    private TableColumn<?, ?> codeIDCol;

    @FXML
    private TableColumn<?, ?> codeAvailablecodeCol;

    @FXML
    private TableColumn<?, ?> codeDiscountCol;

    @FXML
    private TableColumn<?, ?> codeDurationCol;

    @FXML
    private TableColumn<?, ?> codeRemainCol;

    @FXML
    private Button voucherAddBtn;

    @FXML
    private Button voucherClearBtn;

    @FXML
    private TextField voucherCode;

    @FXML
    private Button voucherDeleteBtn;

    @FXML
    private TextField voucherDiscount;

    @FXML
    private DatePicker voucherDuaration;

    @FXML
    private TextField voucherRemain;

    @FXML
    private Button voucherUpdateBtn;

    @FXML
    private TableView<Voucher> voucher_tableView;

    @FXML
    private Label voucher_label_ID;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    public void showData() {
        ObservableList<Voucher> list = localVoucher.getAllVouchers();

        FilteredList<Voucher> filteredData = new FilteredList<>(list, voucher -> true);

        SortedList<Voucher> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(voucher_tableView.comparatorProperty());

        codeAvailablecodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        codeDurationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        codeRemainCol.setCellValueFactory(new PropertyValueFactory<>("remaining"));


        voucher_tableView.setItems(sortedData);
    }

    public void VoucherAddBtn() {
        if (
                        voucherCode.getText().isEmpty()
                        || voucherDiscount.getText().isEmpty()
                        || voucherDuaration.getValue() == null
                        || voucherRemain.getText().isEmpty()

        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly.");
            alert.showAndWait();
            return;
        }

        if (localVoucher.isVoucherExists(voucherCode.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(voucherCode.getText() + " already exists.");
            alert.showAndWait();
        } else {

            boolean success = localVoucher.addVoucher(
                    voucherCode.getText(),
                    Integer.parseInt(voucherRemain.getText()),
                    Float.parseFloat(voucherDiscount.getText()),
                    Date.valueOf(voucherDuaration.getValue())
            );
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Voucher added successfully.");
                alert.showAndWait();
                showData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add voucher.");
                alert.showAndWait();
            }
        }
    }

    public void voucherClearBtn() {
        voucherCode.clear();
        voucherDiscount.clear();
        voucherDuaration.setValue(null);
        voucherRemain.clear();
    }

    public void voucherDeleteBtn() {
        if (voucherCode.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a voucher to delete.");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation Message");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this voucher?");

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = localVoucher.deleteVoucher(voucherCode.getText());

            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Voucher deleted successfully.");
                alert.showAndWait();
                voucherClearBtn();
                showData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete the voucher. Please try again.");
                alert.showAndWait();
            }
        }
    }


    public void voucherUpdateBtn() {
        if (voucherCode.getText().isEmpty()
                || voucherDiscount.getText().isEmpty()
                || voucherDuaration.getValue() == null
                || voucherRemain.getText().isEmpty()
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly.");
            alert.showAndWait();
            return;
        }

        boolean isUpdated = localVoucher.updateVoucher(
                voucherCode.getText(),
                Integer.parseInt(voucherRemain.getText()),
                Float.parseFloat(voucherDiscount.getText()),
                Date.valueOf(voucherDuaration.getValue()).toLocalDate(),
                Integer.parseInt(voucher_label_ID.getText())

        );

        if (isUpdated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Voucher updated successfully.");
            alert.showAndWait();
            showData();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Voucher update failed. Please try again.");
            alert.showAndWait();
        }
    }

    public void voucherSelectedData() {
        Voucher vc = voucher_tableView.getSelectionModel().getSelectedItem();
        int index = voucher_tableView.getSelectionModel().getSelectedIndex();

        if (index < 0 || vc == null) return;
                voucherCode.setText(vc.getCode());
                voucherRemain.setText(String.valueOf(vc.getRemaining()));
                voucherDiscount.setText(String.valueOf(vc.getDiscount()));
                voucherDuaration.setValue(vc.getDuration());
                voucher_label_ID.setText(String.valueOf(vc.getVoucher_id()));
    }

    public void initialize() {
        showData();
    }
}
