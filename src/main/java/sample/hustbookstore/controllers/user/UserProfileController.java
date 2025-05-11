package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.LaunchApplication;
import sample.hustbookstore.controllers.admin.ProfileController;
import sample.hustbookstore.controllers.admin.StoreController;
import sample.hustbookstore.models.AdminList;
import sample.hustbookstore.models.UserList;
import sample.hustbookstore.models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static sample.hustbookstore.LaunchApplication.localAdmin;
import static sample.hustbookstore.LaunchApplication.localUser;

public class UserProfileController {

    @FXML
    private TextField profile_adminAddress;
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
    private Label profile_label_address;

    @FXML
    private Button profile_update_btn;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;


    public void profileFields(){
        profile_adminName.setText(localUser.getName());
        profile_adminPassword.setText(localUser.getPassword());
        profile_adminPhone.setText(localUser.getPhoneNumber());
        profile_adminEmail.setText(localUser.getEmail());
        profile_adminAddress.setText(localUser.getAddress());
    }

    public void profileLabels(){
        profile_label_adminID.setText(String.valueOf(localUser.getAdminId()));
        profile_label_adminName.setText(localUser.getName());
        profile_label_adminUser.setText(localUser.getUsername());
        profile_label_email.setText(localUser.getEmail());
        profile_label_phoneNum.setText(localUser.getPhoneNumber());
        profile_label_address.setText(localUser.getAddress());
    }

    public void profileUpdateBtn(){
        if(profile_adminName.getText().isEmpty()
                || profile_adminPhone.getText().isEmpty()
                || profile_adminEmail.getText().isEmpty()
                || profile_adminAddress.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }else{
            localUser.setName(profile_adminName.getText());
            localUser.setPhoneNumber(profile_adminPhone.getText());
            localUser.setEmail(profile_adminEmail.getText());
            localUser.setAddress(profile_adminAddress.getText());
            UserList.updateUser(localUser);
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
            localUser.setPassword(profile_adminPassword.getText());
            UserList.updateUser(localUser);
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
