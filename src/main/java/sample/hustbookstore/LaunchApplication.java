package sample.hustbookstore;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.hustbookstore.models.*;

import java.io.IOException;

import java.awt.Taskbar;
import java.awt.Toolkit;
import java.awt.Taskbar.Feature;


public class LaunchApplication extends Application {

    @FXML
    private Button admin_btn;

    @FXML
    private StackPane welcomeScreen;

    @FXML
    private Button user_btn;

    public static Admin localAdmin;

    public static User localUser;

    public static Cart localCart = new Cart();

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
        Cart.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(LaunchApplication.class.getResource("LaunchApplication.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 720);
        stage.setTitle("HUST Book Store");

        Image image = new Image(getClass().getResourceAsStream("/sample/hustbookstore/img/logo_meow.png"));

        stage.getIcons().add(image);

        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getResource("/sample/hustbookstore/img/logo_meow.png"));
                taskbar.setIconImage(dockIcon);
            }

        }

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}