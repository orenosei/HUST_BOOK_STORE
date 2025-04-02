package sample.hustbookstore;

import javafx.animation.TranslateTransition;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

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
    private AnchorPane functionArea;

    @FXML
    private AnchorPane headerPane;

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

                Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/LaunchApplication.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/dashboard-view.fxml"));
            functionArea.getChildren().clear();
            functionArea.getChildren().add(root);
            AnchorPane.setBottomAnchor(root, 0.0); // làm cho dashboard dính phía bottom của anchorpane

        }catch(Exception e){
            e.printStackTrace();
        }

        // Hiệu ứng xuất hiện header
        showHeaderAnimation();

    }

    public void loadInventory(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/inventory-view.fxml"));
            functionArea.getChildren().clear();
            functionArea.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }

        headerPane.setVisible(false);
    }

    public void loadStore(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/store-view.fxml"));
            functionArea.getChildren().clear();
            functionArea.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }

        headerPane.setVisible(false);
    }

    public void loadCustomers(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/customers-view.fxml"));
            functionArea.getChildren().clear();
            functionArea.getChildren().add(root);

        }catch(Exception e){
            e.printStackTrace();
        }

        headerPane.setVisible(false);
    }

//    load các chức năng khác, sửa trong cả file fxml (gán fx:id ; onaction click
//    public void loadDashboard(){
//        try{
//            Parent root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/dashboard-view.fxml"));
//            functionArea.getChildren().clear();
//            functionArea.getChildren().add(root);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    headerPane.setVisible(false);

//    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        displayUsername();
        loadDashboard();
    }
}
