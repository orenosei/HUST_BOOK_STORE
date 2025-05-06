package sample.hustbookstore.admin;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button others_btn;

    @FXML
    private AnchorPane home_screen;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private Button store_btn;

    @FXML
    private Text username;


    @FXML
    private AnchorPane headerPane;

    @FXML
    private AnchorPane inventoryScreen;
    @FXML
    private AnchorPane othersScreen;
    @FXML
    private AnchorPane profileScreen;
    @FXML
    private AnchorPane storeScreen;
    @FXML
    private AnchorPane customersScreen;
    @FXML
    private AnchorPane dashboardScreen;

    @FXML
    private Button sync_btn;

    private Alert alert;


    public void logout(){
        try{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            // khi bấm logout, dẫn quay trở lại trang login
            if(option.get().equals(ButtonType.OK)){

                Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/login-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("HUST Book Store");
                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDashboard(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/dashboard-view.fxml"));
            dashboardScreen.getChildren().clear();
            dashboardScreen.getChildren().add(root);
            AnchorPane.setBottomAnchor(root, 0.0); // làm cho dashboard dính phía bottom của anchorpane

        }catch(Exception e){
            e.printStackTrace();
        }
        showHeaderAnimation();

    }

    public void loadInventory(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/inventory-view.fxml"));
            inventoryScreen.getChildren().clear();
            inventoryScreen.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadStore(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/store-view.fxml"));
            storeScreen.getChildren().clear();
            storeScreen.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadCustomers(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/customers-view.fxml"));
            customersScreen.getChildren().clear();
            customersScreen.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadOthers(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/others-view.fxml"));
            othersScreen.getChildren().clear();
            othersScreen.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadProfile(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/profile-view.fxml"));
            profileScreen.getChildren().clear();
            profileScreen.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void showHeaderAnimation() {
        headerPane.setTranslateY(-70); // Ẩn header ban đầu (ra ngoài màn hình)
        headerPane.setVisible(true);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), headerPane);
        tt.setToY(-10); // Trượt xuống vị trí 0
        tt.play();
    }

    public void displayUsername(){
        // đợi 2 anh chị kia làm xong login để lấy username
    }


    @FXML
    private void handleSidebarButtonAction(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboardScreen.setVisible(true);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            sync_btn.setVisible(false);
            showHeaderAnimation();

        } else if (event.getSource() == inventory_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(true);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);
            sync_btn.setVisible(true);

        } else if (event.getSource() == store_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(true);
            headerPane.setVisible(false);
            sync_btn.setVisible(true);

        } else if (event.getSource() == customers_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(true);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);
            sync_btn.setVisible(false);


        } else if (event.getSource() == others_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(true);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);
            sync_btn.setVisible(false);


        } else if (event.getSource() == profile_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(true);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);
            sync_btn.setVisible(false);

        }

    }

    @FXML
    public void handleSyncButtonAction(ActionEvent event){
        if(event.getSource() == sync_btn){loadStore();}
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        displayUsername();
        loadDashboard();
        dashboardScreen.setVisible(true);
        inventoryScreen.setVisible(false);
        customersScreen.setVisible(false);
        othersScreen.setVisible(false);
        profileScreen.setVisible(false);
        storeScreen.setVisible(false);
        sync_btn.setVisible(false);
        //headerPane.setVisible(true);

        loadInventory();
        loadStore();
        loadCustomers();
        loadOthers();
        loadProfile();
    }

}
