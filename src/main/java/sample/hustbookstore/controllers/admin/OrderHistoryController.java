package sample.hustbookstore.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sample.hustbookstore.controllers.base.BaseOrderHistoryController;
import sample.hustbookstore.models.Bill;
import sample.hustbookstore.utils.dao.BillListDAO;
import javafx.scene.control.TableColumn;
import java.util.List;

public class OrderHistoryController extends BaseOrderHistoryController {

    @FXML private Text profitField;
    @FXML private Text totalPriceField;
    @FXML private TableColumn<Bill, String> orderByCol;

    @Override
    public void initializeData() {
        List<Bill> bills = BillListDAO.getAllBills();
        originalBillList.setAll(bills);
    }

    @Override
    public void setupTableColumns() {
        super.setupTableColumns();
        orderByCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getUser().getName()));
    }

    @Override
    @FXML
    public void handleSelectData() {
        super.handleSelectData();
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            totalPriceField.setText(String.format("%.2f", selectedBill.getTotalPrice()));
            profitField.setText(String.format("%.2f", selectedBill.getProfit()));
        }
    }
}