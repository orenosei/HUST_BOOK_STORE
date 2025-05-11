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
import sample.hustbookstore.models.Cart;
import sample.hustbookstore.models.User;
import sample.hustbookstore.models.UserList;
import sample.hustbookstore.models.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localCart;
import static sample.hustbookstore.LaunchApplication.localUser;

public class UserLoginController {
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

    private UserList userList = new UserList();

    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incorrect Username or Password");
        } else if (userList.login(si_username.getText(), si_password.getText())) {
            localCart = Cart.getCartFromDatabase(localUser.getUserId());
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

    public void switchForgotPass(){
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);
        forgotPassQuestionList();
    }

    private Connection connect = database.connectDB();;
    public void proceedBtn(){
        if(fp_username.getText().isEmpty()||fp_question.getSelectionModel().getSelectedItem()==null
                || fp_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            String selectData = "SELECT username, question, answer FROM user WHERE username = ? AND question = ? AND answer = ? ";
            try(PreparedStatement prepare = connect.prepareStatement(selectData)){

                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String)fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                ResultSet result = prepare.executeQuery();
                if(result.next()){
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();

                }
            }catch(Exception e){e.printStackTrace();}

        }

    }

    public void changePassBtn(){
        if(np_newPassword.getText().isEmpty()||np_confirmPassword.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            if(np_newPassword.getText().equals(np_confirmPassword.getText())) {

                String query = "UPDATE user SET password=? WHERE username=?";
                try(PreparedStatement prepare = connect.prepareStatement(query)){

                    prepare.setString(1, np_newPassword.getText());
                    prepare.setString(2, fp_username.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your password updated successfully.");
                    alert.showAndWait();

                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);

                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");
                }catch(Exception e){e.printStackTrace();}


            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not match");
                alert.showAndWait();
            }
        }
    }

    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);

    }

    public void backToQuestionForm() {
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

    public void initialize() {
        regLquestionList();
    }
}
