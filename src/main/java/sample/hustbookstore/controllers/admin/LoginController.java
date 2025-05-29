package sample.hustbookstore.controllers.admin;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.control.*;
import sample.hustbookstore.controllers.base.BaseLoginController;
import sample.hustbookstore.models.Admin;
import sample.hustbookstore.models.AdminList;

public class LoginController extends BaseLoginController {
    private final AdminList adminList = new AdminList();

    @Override
    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        } else if (adminList.login(si_username.getText(), si_password.getText())) {
            loadHomeScreen();
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        }
    }

    @Override
    public void regBtn() {
        Dotenv dotenv = Dotenv.load();
        String adminKey = dotenv.get("ADMIN_KEY");

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() ||
                su_question.getSelectionModel().isEmpty() || su_answer.getText().isEmpty() ||
                su_privacycode.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        } else if (adminList.isUsernameTaken(su_username.getText())) {
            showAlert(Alert.AlertType.ERROR, su_username.getText() + " is already taken");
        } else if (!su_privacycode.getText().equals(adminKey)) {
            showAlert(Alert.AlertType.ERROR, "Privacy code is incorrect");
        } else {
            Admin newAdmin = new Admin(
                    su_username.getText(),
                    su_password.getText(),
                    su_question.getSelectionModel().getSelectedItem(),
                    su_answer.getText()
            );
            if (adminList.registerAdmin(newAdmin)) {
                showAlert(Alert.AlertType.INFORMATION, "Successfully Registered Account!");
                clearRegForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to Register Account");
            }
        }
    }

    @Override
    public void proceedBtn() {
        if (fp_username.getText().isEmpty()
                || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all blank fields");
        } else {
            boolean ok = AdminList.verifySecurityInfo(
                    fp_username.getText(),
                    fp_question.getSelectionModel().getSelectedItem(),
                    fp_answer.getText()
            );
            if (ok) {
                np_newPassForm.setVisible(true);
                fp_questionForm.setVisible(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Incorrect Information");
            }
        }
    }

    @Override
    public void changePassBtn() {
        if (np_newPassword.getText().isEmpty()
                || np_confirmPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all blank fields");
        } else if (!np_newPassword.getText().equals(np_confirmPassword.getText())) {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match");
        } else {
            boolean updated = AdminList.updatePassword(
                    fp_username.getText(),
                    np_newPassword.getText()
            );
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Password updated successfully");
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
                fp_username.clear();
                fp_answer.clear();
                fp_question.getSelectionModel().clearSelection();
                np_newPassword.clear();
                np_confirmPassword.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update password");
            }
        }
    }

    @Override
    protected String getHomeScreenPath() {
        return "/sample/hustbookstore/admin/home-view.fxml";
    }

}
