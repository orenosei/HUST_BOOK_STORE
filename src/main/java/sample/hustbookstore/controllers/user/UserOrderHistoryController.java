package sample.hustbookstore.controllers.user;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.hustbookstore.models.Bill;
import sample.hustbookstore.models.BillItem;
import sample.hustbookstore.utils.dao.BillList;
import java.time.LocalDate;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localUser;

public class UserOrderHistoryController {

    @FXML private AnchorPane billPane;

    @FXML private DatePicker fromDate;

    @FXML private TextField searchBar;

    @FXML private DatePicker toDate;

    @FXML private TableView<Bill> billTable;
    @FXML private TableView<BillItem> showDetailTable;

    @FXML private TableColumn<Bill, Integer> billIdCol;
    @FXML private TableColumn<Bill, LocalDate> purchaseDateCol;

    @FXML private TableColumn<BillItem, String> nameDetailCol;
    @FXML private TableColumn<BillItem, Integer> qtyDetailCol;
    @FXML private TableColumn<BillItem, Double> priceDetailCol;

    private ObservableList<Bill> originalBillList = FXCollections.observableArrayList();
    private FilteredList<Bill> filteredBills;
    @FXML
    public void initialize() {
        setupTableColumns();
        setupDetailTableColumns();
        initializeData();
        setupSearchFunctionality();
    }

    private void initializeData() {
        List<Bill> bills = BillList.getUserBills(localUser.getUserId());
        originalBillList.setAll(bills);
        filteredBills = new FilteredList<>(originalBillList);
        SortedList<Bill> sortedBills = new SortedList<>(filteredBills);
        sortedBills.comparatorProperty().bind(billTable.comparatorProperty());
        billTable.setItems(sortedBills);
    }

    private void setupSearchFunctionality() {
        searchBar.textProperty().addListener((obs, oldVal, newVal) -> filterBills());
        fromDate.valueProperty().addListener((obs, oldVal, newVal) -> filterBills());
        toDate.valueProperty().addListener((obs, oldVal, newVal) -> filterBills());
    }

    private void filterBills() {
        filteredBills.setPredicate(this::matchesFilters);
    }

    private boolean matchesFilters(Bill bill) {
        String searchText = searchBar.getText().toLowerCase();
        String userName = bill.getUser().getName().toLowerCase();
        if (!userName.contains(searchText)) return false;

        LocalDate billDate = bill.getPurchasedDate();
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();

        if (from != null && to != null) {
            return !billDate.isBefore(from) && !billDate.isAfter(to);
        }
        return true;
    }

    private void setupTableColumns() {
        billIdCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getBillId()).asObject());

        purchaseDateCol.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getPurchasedDate()));
    }

    private void setupDetailTableColumns() {
        nameDetailCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getProductName()));

        qtyDetailCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        priceDetailCol.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPriceAtPurchase()).asObject());
    }


    @FXML
    public void handleSelectData() {
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            showDetailTable.setItems(FXCollections.observableArrayList(selectedBill.getItems()));
        }
    }
}
