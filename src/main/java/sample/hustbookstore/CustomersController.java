package sample.hustbookstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.*;
import javafx.beans.property.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class CustomersController {
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, Number> totalColumn;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        ObservableList<Customer> customerList = FXCollections.observableArrayList(
                new Customer("AAAA", "Pham Xuan Thinh", "123456789", "Hell", 694200),
                new Customer("BBBB", "Trinh Minh Thanh", "987654321", "Idk", 230000),
                new Customer("CCCC", "Le Duy Vu", "555123456", "Idk", 120000)
        );

        customerTable.setItems(customerList);
    }
}
