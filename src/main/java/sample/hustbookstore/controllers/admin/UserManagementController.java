package sample.hustbookstore.controllers.admin;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.hustbookstore.models.User;
import sample.hustbookstore.utils.dao.UserList;


public class UserManagementController {

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableView<User> customerTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<User, Double> totalColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    private final FilteredList<User> filteredUsers = new FilteredList<>(UserList.getAllUsers());


    public void initialize() {
        //UserList.initialize();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        totalColumn.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getUserId();
            double total = UserList.getTotalSpentByUserId(userId);
            return new ReadOnlyObjectWrapper<>(total);
        });

        totalColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                if (empty || total == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.2f", total));
                }
            }
        });

        customerTable.setItems(filteredUsers);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String keyword = newVal.toLowerCase();
            filteredUsers.setPredicate(user -> {
                if (keyword.isEmpty()) return true;

                String username = user.getUsername() != null ? user.getUsername().toLowerCase() : "";
                String name = user.getName() != null ? user.getName().toLowerCase() : "";
                String phone = user.getPhoneNumber() != null ? user.getPhoneNumber() : "";

                return username.contains(keyword)
                        || name.contains(keyword)
                        || phone.contains(keyword);
            });
        });
    }
}
