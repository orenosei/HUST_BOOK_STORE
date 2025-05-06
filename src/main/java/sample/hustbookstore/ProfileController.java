package sample.hustbookstore;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileController {

    @FXML
    private TextField profile_adminEmail;

    @FXML
    private TextField profile_adminName;

    @FXML
    private TextField profile_adminPassword;

    @FXML
    private TextField profile_adminPhone;

    @FXML
    private Button profile_changeEmailBtn;

    @FXML
    private Button profile_changeNameBtn;

    @FXML
    private Button profile_changePasswordBtn;

    @FXML
    private Button profile_changePhoneBtn;

    @FXML
    private Button profile_changeRecoAnsBtn;

    @FXML
    private ImageView profile_circleimage;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Label profile_label_adminID;

    @FXML
    private Label profile_label_adminName;

    @FXML
    private Label profile_label_adminUser;

    @FXML
    private Label profile_label_email;

    @FXML
    private Label profile_label_phoneNum;

    @FXML
    private TextField profile_recoAns;

    @FXML
    private ComboBox<String> profile_recoQues;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    private String[] recoQuesList = {"What is your favorite Color?", "What is your favorite food?", "What is your birth date?"};
    public void profileRecoQues(){
        List<String> listS = new ArrayList<>();
        for(String data: recoQuesList){
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableList(listS);
        profile_recoQues.setItems(listData);
    };

    public void profileFields(){
        String selectData = "SELECT * FROM admin WHERE id = '"
                + profile_label_adminID.getText() + "'";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            if(result.next()){
                profile_adminName.setText(result.getString("name"));
                profile_adminPassword.setText(result.getString("password"));
                profile_adminPhone.setText(result.getString("phonenumber"));
                profile_adminEmail.setText(result.getString("email"));
            }
        }catch(Exception e) {e.printStackTrace();}
    }

    public void profileLabels(){
        String selectData = "SELECT * FROM admin WHERE id = '"
                    + profile_label_adminID.getText() + "'";

        connect = database.connectDB();
        try{
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();
            if(result.next()){
                    profile_label_adminID.setText(result.getString("id"));
                    profile_label_adminName.setText(result.getString("name"));
                    profile_label_adminUser.setText(result.getString("username"));
                    profile_label_email.setText(result.getString("email"));
                    profile_label_phoneNum.setText(result.getString("phonenumber"));
            }
        }catch(Exception e) {e.printStackTrace();}
    }

    public void profileUpdateBtn(){
        connect = database.connectDB();
        if(profile_adminName.getText().isEmpty()
                || profile_adminPhone.getText().isEmpty()
                || profile_adminEmail.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }else{
            String updateData = "UPDATE admin SET name = ?, phonenumber = ?, email = ? "
                    + "WHERE id = '"
                    + profile_label_adminID.getText() + "'";
            try{
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, profile_adminName.getText());
                prepare.setString(2, profile_adminPhone.getText());
                prepare.setString(3, profile_adminEmail.getText());
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Your information updated successfully.");
                alert.showAndWait();

                profileLabels();
            }catch(Exception e) {e.printStackTrace();}

        }
    }

    public void profileChangePasswordBtn(){
        connect = database.connectDB();
        if(profile_adminPassword.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter new password");
            alert.showAndWait();
        }else{
            String updateData = "UPDATE admin SET password = ? "
                    + "WHERE id = '"
                    + profile_adminPassword.getText() + "'";
            try {
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, profile_adminPassword.getText());
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Your password updated successfully.");
                alert.showAndWait();

            }catch(Exception e) {e.printStackTrace();}
        }
    }

    private String localID = "1";


    public void initialize(){

        profile_label_adminID.setText(localID);
        profileLabels();
        profileFields();
        profileRecoQues();

    }


}
