package sample.hustbookstore;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.hustbookstore.models.*;

import java.io.IOException;
import java.util.Date;


public class LaunchApplication extends Application {

    @FXML
    private Button admin_btn;

    @FXML
    private StackPane welcomeScreen;

    @FXML
    private Button user_btn;

    public static Admin localAdmin;

    public static User localUser;

    public static VoucherList localVoucher = new VoucherList();

    public static Inventory localInventory = new Inventory();

    public static Store localStore = new Store();



    public void switchAdminLogin() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/login-view.fxml"));
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = (Stage) welcomeScreen.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchUserLogin() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/user/user-login-view.fxml"));
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = (Stage) welcomeScreen.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void start(Stage stage) throws IOException {
        localAdmin = null;
        localUser = null;
        AdminList.initialize();
        UserList.initialize();
        Inventory.initialize();
        Store.initialize();
        VoucherList.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(LaunchApplication.class.getResource("LaunchApplication.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 720);
        stage.setTitle("Welcome to HUST Book Store");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}