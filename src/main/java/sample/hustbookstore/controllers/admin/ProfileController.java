package sample.hustbookstore.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import sample.hustbookstore.controllers.base.BaseProfileController;
import sample.hustbookstore.models.AdminList;

import static sample.hustbookstore.LaunchApplication.localAdmin;

public class ProfileController extends BaseProfileController {
    @Override
    protected void loadProfileFields() {
        profile_adminName.setText(localAdmin.getName());
        profile_adminPassword.setText(localAdmin.getPassword());
        profile_adminPhone.setText(localAdmin.getPhoneNumber());
        profile_adminEmail.setText(localAdmin.getEmail());
    }

    @Override
    protected void loadProfileLabels() {
        profile_label_adminID.setText(String.valueOf(localAdmin.getAdminId()));
        profile_label_adminName.setText(localAdmin.getName());
        profile_label_adminUser.setText(localAdmin.getUsername());
        profile_label_email.setText(localAdmin.getEmail());
        profile_label_phoneNum.setText(localAdmin.getPhoneNumber());
    }

    @Override
    protected void updateProfile() {
        if (profile_adminName.getText().isEmpty()
                || profile_adminPhone.getText().isEmpty()
                || profile_adminEmail.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all the fields");
        } else {
            localAdmin.setName(profile_adminName.getText());
            localAdmin.setPhoneNumber(profile_adminPhone.getText());
            localAdmin.setEmail(profile_adminEmail.getText());
            AdminList.updateAdmin(localAdmin);
            loadProfileLabels();
            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Your information updated successfully.");
        }
    }

    @Override
    protected void changePassword() {
        if (profile_adminPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter new password");
        } else {
            localAdmin.setPassword(profile_adminPassword.getText());
            AdminList.updateAdmin(localAdmin);
            loadProfileLabels();
            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Your password updated successfully.");
        }
    }

    @FXML
    private void handleUpdateBtn() {
        updateProfile();
    }

    @FXML
    private void handleChangePasswordBtn() {
        changePassword();
    }
}