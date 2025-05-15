package sample.hustbookstore.controllers.admin;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import sample.hustbookstore.models.Admin;
import sample.hustbookstore.models.AdminList;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;



public class LoginController {
    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private TextField fp_username;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private PasswordField su_privacycode;

    @FXML
    private AnchorPane waitingScreen;

    private Alert alert;

    private AdminList adminList = new AdminList();

    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        } else if (adminList.login(si_username.getText(), si_password.getText())) {
            loadHomeScreen();
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        }
    }

    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() ||
                su_question.getSelectionModel().isEmpty() || su_answer.getText().isEmpty() || su_privacycode.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        } else if (adminList.isUsernameTaken(su_username.getText())) {
            showAlert(Alert.AlertType.ERROR, su_username.getText() + " is already taken");
        } else if (!su_privacycode.getText().equals("abc123")) {
            showAlert(Alert.AlertType.ERROR, "Privacy code is incorrect");
        } else {
            Admin newAdmin = new Admin(
                    su_username.getText(),
                    su_password.getText(),
                    (String) su_question.getSelectionModel().getSelectedItem(),
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

    private void loadHomeScreen() {
        waitingScreen.setVisible(true);

        Task<AnchorPane> loadTask = new Task<>() {
            @Override
            protected AnchorPane call() throws IOException {
                return FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/home-view.fxml"));
            }
        };

        loadTask.setOnSucceeded(event -> {
            try {
                AnchorPane root = loadTask.getValue();
                Stage currentStage = (Stage) si_loginBtn.getScene().getWindow();
                currentStage.close();

                Stage stage = new Stage();
                stage.setTitle("HUSTBookStore");
                stage.setScene(new Scene(root));
                stage.setMinWidth(1280);
                stage.setMinHeight(720);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Failed to display main window!");
            } finally {
                waitingScreen.setVisible(false);
            }
        });

        loadTask.setOnFailed(event -> {
            waitingScreen.setVisible(false);
            showAlert(Alert.AlertType.ERROR, "Failed to load main window!");
        });

        new Thread(loadTask).start();
    }


    private void showAlert(Alert.AlertType type, String content) {
        alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error Message" : "Information Message");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearRegForm() {
        su_username.setText("");
        su_password.setText("");
        su_privacycode.setText("");
        su_question.getSelectionModel().clearSelection();
        su_answer.setText("");
    }


    private String[] questionList = {"What is your favorite movie?", "What is your favorite book?", "What is your favorite sport?", "What is your favorite song?", "What is your favorite drink?", "What is your favorite food?", "What is your favorite game?", "What is your favorite animal?", "What is your favorite color?"};
    public void regLquestionList(){
        List<String> listQ = new ArrayList<>();

        for(String data: questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableList(listQ);
        su_question.setItems(listData);
    }

    public void switchForgotPass(){
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }

    public void proceedBtn() {
        if (fp_username.getText().isEmpty()
                || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all blank fields");
        } else {
            boolean ok = AdminList.verifySecurityInfo(
                    fp_username.getText(),
                    (String) fp_question.getSelectionModel().getSelectedItem(),
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
                showAlert(Alert.AlertType.INFORMATION, "Your password updated successfully.");
                // chuyển về form login và reset các trường
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
                fp_username.clear();
                fp_answer.clear();
                fp_question.getSelectionModel().clearSelection();
                np_newPassword.clear();
                np_confirmPassword.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update password. Please try again.");
            }
        }
    }


    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);

    }

    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    public void forgotPassQuestionList(){
        List<String> listQ = new ArrayList<>();
        for(String data: questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableList(listQ);
        fp_question.setItems(listData);

    }

    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        slider.setNode(side_form);

        if (event.getSource() == side_CreateBtn) {
            slider.setToX(640);

        } else if (event.getSource() == side_alreadyHave) {
            slider.setToX(0);

        }

        slider.setDuration(Duration.seconds(.5));
        slider.setOnFinished(e -> {
            side_CreateBtn.setVisible(!side_CreateBtn.isVisible());
            side_alreadyHave.setVisible(!side_alreadyHave.isVisible());
            fp_questionForm.setVisible(false);
            si_loginForm.setVisible(true);
            np_newPassForm.setVisible(false);
        });

        slider.play();
    }

    public void initialize(){
        regLquestionList();
    }
}
