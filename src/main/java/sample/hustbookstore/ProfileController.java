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
                profile_adminName.setText(result.getString("admin_name"));
                profile_adminPassword.setText(result.getString("admin_password"));
                profile_adminPhone.setText(result.getString("admin_phone"));
                profile_adminEmail.setText(result.getString("admin_email"));
                profile_recoAns.setText(result.getString("reco_ans"));
                profile_recoQues.setValue(result.getString("reco_ques"));
                profile_recoQues.getSelectionModel().select(result.getString("reco_ques"));
            }
        }catch(Exception e) {e.printStackTrace();}
    }

    public void profilabels(){
        String selectData = "SELECT * FROM admin WHERE id = '"
                + profile_label_adminID.getText() + "'";

        connect = database.connectDB();
    }

    public void initialize(){
        profileFields();
        profileRecoQues();

    }

}
