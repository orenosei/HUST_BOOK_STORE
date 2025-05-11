package sample.hustbookstore.controllers.admin;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.LaunchApplication;
import sample.hustbookstore.models.AdminList;
import sample.hustbookstore.models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static sample.hustbookstore.LaunchApplication.localAdmin;

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
    private Button profile_update_btn;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;


    public void profileFields(){
        profile_adminName.setText(localAdmin.getName());
        profile_adminPassword.setText(localAdmin.getPassword());
        profile_adminPhone.setText(localAdmin.getPhoneNumber());
        profile_adminEmail.setText(localAdmin.getEmail());
    }

    public void profileLabels(){
        profile_label_adminID.setText(String.valueOf(localAdmin.getAdminId()));
        profile_label_adminName.setText(localAdmin.getName());
        profile_label_adminUser.setText(localAdmin.getUsername());
        profile_label_email.setText(localAdmin.getEmail());
        profile_label_phoneNum.setText(localAdmin.getPhoneNumber());
    }

    public void profileUpdateBtn(){
        if(profile_adminName.getText().isEmpty()
                || profile_adminPhone.getText().isEmpty()
                || profile_adminEmail.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }else{
            localAdmin.setName(profile_adminName.getText());
            localAdmin.setPhoneNumber(profile_adminPhone.getText());
            localAdmin.setEmail(profile_adminEmail.getText());
            AdminList.updateAdmin(localAdmin);
            profileLabels();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Your information updated successfully.");
            alert.showAndWait();
        }
    }

    public void profileChangePasswordBtn(){
        if(profile_adminPassword.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter new password");
        }
        else{
            localAdmin.setPassword(profile_adminPassword.getText());
            AdminList.updateAdmin(localAdmin);
            profileLabels();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Your password updated successfully.");
            alert.showAndWait();
        }
    }

    public void initialize(){
        profileLabels();
        profileFields();
    }
}
