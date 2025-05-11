package sample.hustbookstore.controllers.user;

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
import sample.hustbookstore.models.User;
import sample.hustbookstore.models.UserList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLoginController {
    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

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
    private AnchorPane waitingScreen;

    private Alert alert;

    private UserList userList = new UserList();

    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        } else if (userList.login(si_username.getText(), si_password.getText())) {
            loadHomeScreen();
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        }
    }

    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() ||
                su_question.getSelectionModel().isEmpty() || su_answer.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        } else if (userList.isUsernameTaken(su_username.getText())) {
            showAlert(Alert.AlertType.ERROR, su_username.getText() + " is already taken");
        } else if (su_password.getText().length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Invalid Password, at least 8 characters are needed");
        } else {
            User newUser = new User(
                    su_username.getText(),
                    su_password.getText(),
                    (String) su_question.getSelectionModel().getSelectedItem(),
                    su_answer.getText()
            );
            if (userList.registerUser(newUser)) {
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
                return FXMLLoader.load(getClass().getResource("/sample/hustbookstore/user/user-home-view.fxml"));
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
        su_question.getSelectionModel().clearSelection();
        su_answer.setText("");
    }

    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "What is your birth date?"};
    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableList(listQ);
        su_question.setItems(listData);
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
        });

        slider.play();
    }

    public void initialize() {
        regLquestionList();
    }
}
