package sample.hustbookstore.controllers.user;

import javafx.scene.control.*;
import sample.hustbookstore.controllers.base.BaseLoginController;
import sample.hustbookstore.models.User;
import sample.hustbookstore.utils.dao.CartListDAO;
import sample.hustbookstore.utils.dao.UserListDAO;
import static sample.hustbookstore.LaunchApplication.*;

public class UserLoginController extends BaseLoginController {
    @Override
    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter both username and password.");
        } else {
            int loginResult = UserListDAO.login(si_username.getText(), si_password.getText());
            if (loginResult == 1) {
                localCart = CartListDAO.getCartFromDatabase(localUser.getUserId());
                loadHomeScreen();
            } else if (loginResult == -1) { //banned acc
                showAlert(Alert.AlertType.WARNING, "Your account has been banned. Please contact the administrator.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Incorrect username or password.");
            }
        }
    }

    @Override
    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() ||
                su_question.getSelectionModel().isEmpty() || su_answer.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        } else if (UserListDAO.isUsernameTaken(su_username.getText())) {
            showAlert(Alert.AlertType.ERROR, su_username.getText() + " is already taken");
        } else {
            User newUser = new User(
                    su_username.getText(),
                    su_password.getText(),
                    su_question.getSelectionModel().getSelectedItem(),
                    su_answer.getText()
            );
            if (UserListDAO.registerUser(newUser)) {
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
            boolean ok = UserListDAO.verifySecurityInfo(
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
            boolean updated = UserListDAO.updatePassword(
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
    public String getHomeScreenPath() {
        return "/sample/hustbookstore/user/user-home-view.fxml";
    }

}
