package sample.hustbookstore.controllers.base;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import sample.hustbookstore.models.Bill;
import sample.hustbookstore.models.BillItem;

import java.time.LocalDate;
import java.util.List;

public abstract class BaseOrderHistoryController {
    @FXML protected DatePicker fromDate;
    @FXML protected TextField searchBar;
    @FXML protected DatePicker toDate;
    @FXML protected TableView<Bill> billTable;
    @FXML protected TableView<BillItem> showDetailTable;

    @FXML protected TableColumn<Bill, Integer> billIdCol;
    @FXML protected TableColumn<Bill, LocalDate> purchaseDateCol;

    @FXML protected TableColumn<BillItem, String> nameDetailCol;
    @FXML protected TableColumn<BillItem, Integer> qtyDetailCol;
    @FXML protected TableColumn<BillItem, Double> priceDetailCol;

    protected ObservableList<Bill> originalBillList = FXCollections.observableArrayList();
    protected FilteredList<Bill> filteredBills;
    protected SortedList<Bill> sortedBills;

    public abstract void initializeData();


    public void setupTableColumns() {
        billIdCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getBillId()).asObject());
        purchaseDateCol.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getPurchasedDate()));
    }

    public void setupDetailTableColumns() {
        nameDetailCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getProductName()));
        qtyDetailCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        priceDetailCol.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPriceAtPurchase()).asObject());
    }

    public void setupFilteredSortedLists() {
        filteredBills = new FilteredList<>(originalBillList, p -> true);
        sortedBills = new SortedList<>(filteredBills);
        sortedBills.comparatorProperty().bind(billTable.comparatorProperty());
        billTable.setItems(sortedBills);
    }

    public void setupSearchFunctionality() {
        searchBar.textProperty().addListener((obs, oldVal, newVal) -> filterBills());
        fromDate.valueProperty().addListener((obs, oldVal, newVal) -> filterBills());
        toDate.valueProperty().addListener((obs, oldVal, newVal) -> filterBills());
    }

    public void filterBills() {
        filteredBills.setPredicate(bill -> {
            String searchText = searchBar.getText().toLowerCase();
            String userName = bill.getUser().getName().toLowerCase();
            if (!userName.contains(searchText)) return false;

            LocalDate billDate = bill.getPurchasedDate();
            LocalDate from = fromDate.getValue();
            LocalDate to = toDate.getValue();

            if (from != null && billDate.isBefore(from)) return false;
            if (to != null && billDate.isAfter(to)) return false;
            return true;
        });
    }

    @FXML
    public void handleSelectData() {
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            showDetailTable.setItems(FXCollections.observableArrayList(selectedBill.getItems()));
        }
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        setupDetailTableColumns();
        initializeData();
        setupFilteredSortedLists();
        setupSearchFunctionality();
    }
}
