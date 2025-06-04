package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import sample.hustbookstore.controllers.base.BaseProfileController;
import sample.hustbookstore.utils.dao.UserList;

import static sample.hustbookstore.LaunchApplication.localUser;

public class UserProfileController extends BaseProfileController {
    @Override
    public void loadProfileFields() {
        profile_adminName.setText(localUser.getName());
        profile_adminPassword.setText(localUser.getPassword());
        profile_adminPhone.setText(localUser.getPhoneNumber());
        profile_adminEmail.setText(localUser.getEmail());
        profile_adminAddress.setText(localUser.getAddress());
    }

    @Override
    public void loadProfileLabels() {
        profile_label_adminID.setText(String.valueOf(localUser.getUserId()));
        profile_label_adminName.setText(localUser.getName());
        profile_label_adminUser.setText(localUser.getUsername());
        profile_label_email.setText(localUser.getEmail());
        profile_label_phoneNum.setText(localUser.getPhoneNumber());
        profile_label_address.setText(localUser.getAddress());
    }

    @Override
    public void updateProfile() {
        if (profile_adminName.getText().isEmpty()
                || profile_adminPhone.getText().isEmpty()
                || profile_adminEmail.getText().isEmpty()
                || profile_adminAddress.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all the fields");
        } else {
            localUser.setName(profile_adminName.getText());
            localUser.setPhoneNumber(profile_adminPhone.getText());
            localUser.setEmail(profile_adminEmail.getText());
            localUser.setAddress(profile_adminAddress.getText());
            UserList.updateUser(localUser);
            loadProfileLabels();
            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Your information updated successfully.");
        }
    }

    @Override
    public void changePassword() {
        if (profile_adminPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter new password");
        } else {
            localUser.setPassword(profile_adminPassword.getText());
            UserList.updateUser(localUser);
            loadProfileLabels();
            showAlert(Alert.AlertType.INFORMATION, "Information Message", "Your password updated successfully.");
        }
    }

    @FXML
    public void handleUpdateBtn() {
        updateProfile();
    }

    @FXML
    public void handleChangePasswordBtn() {
        changePassword();
    }
}