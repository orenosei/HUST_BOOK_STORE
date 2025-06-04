package sample.hustbookstore.controllers.admin;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.hustbookstore.utils.dao.BillList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class OrderHistoryController {

    @FXML private AnchorPane billPane;

    @FXML private TableView<Map<String, Object>> billTable;

    @FXML private DatePicker fromDate;

    @FXML private Text profitField;

    @FXML private TextField searchBar;

    @FXML private Button searchBtn;

    @FXML private TableView<Map<String, Object>> showDetailTable;

    @FXML private TableColumn<Map<String, Object>, String> nameDetailCol;

    @FXML private TableColumn<Map<String, Object>, Integer> qtyDetailCol;

    @FXML private TableColumn<Map<String, Object>, Double> priceDetailCol;

    @FXML private DatePicker toDate;

    @FXML private Text totalPriceField;

    @FXML private TableColumn<Map<String, Object>, Integer> billIdCol;

    @FXML private TableColumn<Map<String, Object>, String> orderByCol;

    @FXML private TableColumn<Map<String, Object>, LocalDate> purchaseDateCol;

    private ObservableList<Map<String, Object>> originalBillList = FXCollections.observableArrayList();
    private FilteredList<Map<String, Object>> filteredBills;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupDetailTableColumns();
        initializeData();
        setupSearchFunctionality();
    }

    private void initializeData() {
        List<Map<String, Object>> bills = BillList.getAllBillsWithUserName();
        originalBillList.setAll(bills);
        filteredBills = new FilteredList<>(originalBillList);
        billTable.setItems(filteredBills);
    }

    private void setupSearchFunctionality() {
        searchBar.textProperty().addListener((obs, oldVal, newVal) -> filterBills());
        fromDate.valueProperty().addListener((obs, oldVal, newVal) -> filterBills());
        toDate.valueProperty().addListener((obs, oldVal, newVal) -> filterBills());
    }

    private void filterBills() {
        filteredBills.setPredicate(this::matchesFilters);
    }

    private boolean matchesFilters(Map<String, Object> bill) {
        String searchText = searchBar.getText().toLowerCase();
        String userName = ((String) bill.get("name")).toLowerCase();
        if (!userName.contains(searchText)) return false;

        LocalDate billDate = (LocalDate) bill.get("purchase_date");
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();

        if (from != null && to != null) {
            return !billDate.isBefore(from) && !billDate.isAfter(to);
        }
        return true;
    }

    private void setupTableColumns() {
        billIdCol.setCellValueFactory(data ->
                new SimpleIntegerProperty((Integer) data.getValue().get("bill_id")).asObject());

        orderByCol.setCellValueFactory(data ->
                new SimpleStringProperty((String) data.getValue().get("name")));

        purchaseDateCol.setCellValueFactory(data ->
                new SimpleObjectProperty<>((LocalDate) data.getValue().get("purchase_date")));
    }

    private void setupDetailTableColumns() {
        nameDetailCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().get("product_name").toString()));

        qtyDetailCol.setCellValueFactory(data ->
                new SimpleIntegerProperty((Integer) data.getValue().get("quantity")).asObject());

        priceDetailCol.setCellValueFactory(data ->
                new SimpleDoubleProperty((Double) data.getValue().get("price")).asObject());
    }

    @FXML
    public void handleSearch() {
        filterBills();
    }

    @FXML
    public void handleSelectData() {
        Map<String, Object> selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            int billId = (Integer) selectedBill.get("bill_id");
            List<Map<String, Object>> items = BillList.getBillItemsWithProductName(billId);
            showDetailTable.setItems(FXCollections.observableArrayList(items));

            double total = (Double) selectedBill.get("total_price");
            double profit = (Double) selectedBill.get("profit");
            totalPriceField.setText(String.format("%.2f", total));
            profitField.setText(String.format("%.2f", profit));
        }
    }

}