package sample.hustbookstore.controllers.base;

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
import sample.hustbookstore.LaunchApplication;

import java.io.IOException;

public abstract class BaseLoginController {

    @FXML protected TextField fp_answer;
    @FXML protected Button fp_back;
    @FXML protected Button fp_proceedBtn;
    @FXML protected ComboBox<String> fp_question;
    @FXML protected TextField fp_username;
    @FXML protected AnchorPane fp_questionForm;
    @FXML protected Button np_back;
    @FXML protected Button np_changePassBtn;
    @FXML protected PasswordField np_confirmPassword;
    @FXML protected AnchorPane np_newPassForm;
    @FXML protected PasswordField np_newPassword;
    @FXML protected Hyperlink si_forgotPass;
    @FXML protected Button si_loginBtn;
    @FXML protected PasswordField si_password;
    @FXML protected TextField si_username;
    @FXML protected AnchorPane si_loginForm;
    @FXML protected Button side_CreateBtn;
    @FXML protected AnchorPane side_form;
    @FXML protected TextField su_answer;
    @FXML protected PasswordField su_password;
    @FXML protected ComboBox<String> su_question;
    @FXML protected Button su_signupBtn;
    @FXML protected AnchorPane su_signupForm;
    @FXML protected TextField su_username;
    @FXML protected Button side_alreadyHave;
    @FXML protected PasswordField su_privacycode;
    @FXML protected AnchorPane waitingScreen;
    @FXML
    protected Button returnBtn;

    protected Alert alert;
    protected final String[] questionList = {
            "What is your favorite movie?", "What is your favorite book?",
            "What is your favorite sport?", "What is your favorite song?",
            "What is your favorite drink?", "What is your favorite food?",
            "What is your favorite game?", "What is your favorite animal?",
            "What is your favorite color?"
    };

    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        slider.setNode(side_form);
        slider.setDuration(Duration.seconds(.5));

        if (event.getSource() == side_CreateBtn) {
            slider.setToX(640);
        } else if (event.getSource() == side_alreadyHave) {
            slider.setToX(0);
        }

        slider.setOnFinished(e -> {
            side_CreateBtn.setVisible(!side_CreateBtn.isVisible());
            side_alreadyHave.setVisible(!side_alreadyHave.isVisible());
        });
        slider.play();
    }

    public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }

    public void backToLoginForm() {
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    public void backToQuestionForm() {
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    protected void showAlert(Alert.AlertType type, String content) {
        alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error Message" : "Information Message");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected void clearRegForm() {
        su_username.setText("");
        su_password.setText("");
        su_question.getSelectionModel().clearSelection();
        su_answer.setText("");
        if (su_privacycode != null) {
            su_privacycode.setText("");
        }
    }

    public void regQuestionList() {
        ObservableList<String> listData = FXCollections.observableArrayList(questionList);
        su_question.setItems(listData);
    }

    public void forgotPassQuestionList() {
        ObservableList<String> listData = FXCollections.observableArrayList(questionList);
        fp_question.setItems(listData);
    }

    public void initialize() {
        regQuestionList();
    }

    protected void loadHomeScreen() {
        waitingScreen.setVisible(true);

        Task<AnchorPane> loadTask = new Task<>() {
            @Override
            protected AnchorPane call() throws IOException {
                return FXMLLoader.load(getClass().getResource(getHomeScreenPath()));
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

    @FXML
    protected void handleReturnButton(ActionEvent event) {
        Stage stage = (Stage) returnBtn.getScene().getWindow();
        stage.setScene(LaunchApplication.welcomeScene);
        stage.centerOnScreen();
    }

    protected abstract void loginBtn();
    protected abstract void regBtn();
    protected abstract void proceedBtn();
    protected abstract void changePassBtn();
    protected abstract String getHomeScreenPath();
}