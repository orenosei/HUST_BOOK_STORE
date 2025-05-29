package sample.hustbookstore.controllers.base;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.models.AdminList;
import sample.hustbookstore.models.UserList;

public abstract class BaseProfileController {
    @FXML
    protected TextField profile_adminEmail;
    @FXML
    protected TextField profile_adminName;
    @FXML
    protected TextField profile_adminPassword;
    @FXML
    protected TextField profile_adminPhone;
    @FXML
    protected TextField profile_adminAddress;
    @FXML
    protected ImageView profile_circleimage;
    @FXML
    protected AnchorPane profile_form;
    @FXML
    protected Button profile_importBtn;
    @FXML
    protected Label profile_label_adminID;
    @FXML
    protected Label profile_label_adminName;
    @FXML
    protected Label profile_label_adminUser;
    @FXML
    protected Label profile_label_email;
    @FXML
    protected Label profile_label_phoneNum;
    @FXML
    protected Label profile_label_address;
    @FXML
    protected Button profile_update_btn;

    protected Alert alert;

    protected abstract void updateProfile();
    protected abstract void changePassword();
    protected abstract void loadProfileFields();
    protected abstract void loadProfileLabels();

    @FXML
    protected void initialize() {
        loadProfileLabels();
        loadProfileFields();
    }

    protected void showAlert(Alert.AlertType type, String title, String content) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}