package sample.hustbookstore.controllers.admin;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.hustbookstore.models.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

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
    private PasswordField su_privacycode;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    private static String userName;
    public static String getUserName(){
        return userName;
    }

    public void loginBtn(){
        if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username or Password");
            alert.showAndWait();
        } else {
            String selectData = "SELECT username, password FROM admin WHERE username = ? AND password = ?";
            connect = database.connectDB();
            try{
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                userName = si_username.getText();
                prepare.setString(2, si_password.getText());
                result = prepare.executeQuery();

                if(result.next()){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully logged in!");
                    alert.showAndWait();

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));
                        Stage currentStage = (Stage) si_loginBtn.getScene().getWindow();
                        currentStage.close();

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.setTitle("HUSTBookStore");
                        stage.setMinWidth(1280);
                        stage.setMinHeight(720);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to load main window!");
                        alert.showAndWait();
                    }

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username or Password");
                    alert.showAndWait();

                }
            }catch (Exception e){e.printStackTrace();}
        }
    }

    public void regBtn(){
        if(su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty() || su_privacycode.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR); //???
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO admin (username, password, question, answer) "
                    + "  VALUES (?,?,?,?)";
            connect = database.connectDB();


            try {
                String checkUsername = "SELECT username FROM admin WHERE username = '"
                        + su_username.getText() + "'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR); //???
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText()+ "is already taken");
                    alert.showAndWait();
                }else if(su_password.getText().length()<8){
                    alert = new Alert(Alert.AlertType.ERROR); //???
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 8 characters are needed");
                    alert.showAndWait();

                } else if(!su_privacycode.getText().equals("abc123")){
                    alert = new Alert(Alert.AlertType.ERROR); //???
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Privacy code is incorrect");
                    alert.showAndWait();
                } //???????*/
                else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String)su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());
                    // prepare.setString(5, su_privacycode.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION); //???
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered Account!");
                    alert.showAndWait();
                    su_username.setText("");
                    su_password.setText("");
                    // su_privacycode.setText(""); //???
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition(/*Duration.millis(1500), su_privacycode*/);

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_CreateBtn.setVisible(true);

                    });
                    slider.play();
                }



            }catch(Exception e) {e.printStackTrace();}
        }
    }

    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "What is your birth date?"};
    public void regLquestionList(){
        List<String> listQ = new ArrayList<>();

        for(String data: questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableList(listQ);
        su_question.setItems(listData);
    }

    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(640);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
                regLquestionList();

            });
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);

            });
            slider.play();
        }

    }
}
