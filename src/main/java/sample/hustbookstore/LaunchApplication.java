package sample.hustbookstore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.hustbookstore.controllers.admin.HomeScreenController;
import sample.hustbookstore.controllers.user.UserHomeScreenController;
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

    public static Admin localAdmin = new Admin();

    public static User localUser = new User();

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


//
//    private void preloadHomeViews() {
//        new Thread(() -> {
//            // Load admin home view on FX thread
//            Platform.runLater(() -> {
//                try {
//                    adminHomeRoot = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/home-view.fxml"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            // Load user home view on FX thread
//            Platform.runLater(() -> {
//                try {
//                    userHomeRoot = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/user/user-home-view.fxml"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }).start();
//    }

    @Override
    public void start(Stage stage) throws IOException {
//        localAdmin = null;
//        localUser = null;
        AdminList.initialize();
        UserList.initialize();
        Inventory.initialize();
        //Store.initialize();
        VoucherList.initialize();
        Cart.initialize();
        //preloadHomeViews();
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
//    public static AnchorPane adminHomeRoot;
//    public static AnchorPane userHomeRoot;
//    private Stage primaryStage;
//
//    @Override
//    public void start(Stage stage) {
//        this.primaryStage = stage;
//
//        Task<Void> preloadTask = new Task<>() {
//            @Override
//            protected Void call() {
//                initializeData();
//                Platform.runLater(() -> {
//                    try {
//                        loadFXMLResources();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//                return null;
//            }
//        };
//
//        preloadTask.setOnSucceeded(e -> showMainApplication());
//        new Thread(preloadTask).start();
//    }
//
//    private void initializeData() {
//        AdminList.initialize();
//        UserList.initialize();
//        Inventory.initialize();
//        VoucherList.initialize();
//        Cart.initialize();
//    }
//
////    private void loadFXMLResources() throws IOException {
////        adminHomeRoot = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/admin/home-view.fxml"));
////        userHomeRoot = FXMLLoader.load(getClass().getResource("/sample/hustbookstore/user/user-home-view.fxml"));
////    }
//
//    public static HomeScreenController adminHomeController;
//    public static UserHomeScreenController userHomeController;
//
//    private void loadFXMLResources() throws IOException {
//        // Load admin home
//        FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/admin/home-view.fxml"));
//        adminHomeRoot = adminLoader.load();
//        adminHomeController = adminLoader.getController();
//
//        // Load user home
//        FXMLLoader userLoader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/user/user-home-view.fxml"));
//        userHomeRoot = userLoader.load();
//        userHomeController = userLoader.getController();
//    }
//
//    private void showMainApplication() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("LaunchApplication.fxml"));
//            StackPane root = loader.load();
//
//            Scene scene = new Scene(root, 640, 720);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("HUST Book Store");
//            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/sample/hustbookstore/img/logo_meow.png")));
//            if (Taskbar.isTaskbarSupported()) {
//                var taskbar = Taskbar.getTaskbar();
//
//                if (taskbar.isSupported(Feature.ICON_IMAGE)) {
//                    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
//                    var dockIcon = defaultToolkit.getImage(getClass().getResource("/sample/hustbookstore/img/logo_meow.png"));
//                    taskbar.setIconImage(dockIcon);
//                }
//
//            }
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        launch();
    }
}