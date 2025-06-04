package sample.hustbookstore.controllers.admin;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML
    private Button banUserBtn;

    @FXML
    private Button unbanUserBtn;


    private final FilteredList<User> filteredUsers = new FilteredList<>(UserList.getAllUsers());

    @FXML
    public void handleBanUser() {
        User selectedUser = customerTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean success = UserList.banUser(selectedUser.getUserId());
            if (success) {
                selectedUser.setBanned(true);
                customerTable.refresh();
                showAlert(Alert.AlertType.INFORMATION, "User has been banned successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to ban the user.");
            }
        }
    }

    @FXML
    public void handleUnbanUser() {
        User selectedUser = customerTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean success = UserList.unbanUser(selectedUser.getUserId());
            if (success) {
                selectedUser.setBanned(false);
                customerTable.refresh();
                showAlert(Alert.AlertType.INFORMATION, "User has been unbanned successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to unban the user.");
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize() {
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

        customerTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setStyle("");
                } else if (user.isBanned()) {
                    setStyle("-fx-background-color: #ea6262;");
                } else {
                    setStyle("");
                }
            }
        });

        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                banUserBtn.setDisable(true);
                unbanUserBtn.setDisable(true);
            } else if (newSelection.isBanned()) {
                banUserBtn.setDisable(true);
                unbanUserBtn.setDisable(false);
            } else {
                banUserBtn.setDisable(false);
                unbanUserBtn.setDisable(true);
            }
        });
    }
}
