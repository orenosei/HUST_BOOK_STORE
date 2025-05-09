package sample.hustbookstore.controllers.admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

import static sample.hustbookstore.LaunchApplication.localAdmin;

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

    protected String getLaunchApplicationPath() {
        return "/sample/hustbookstore/LaunchApplication.fxml";
    }

    protected String getDashboardPath() {
        return "/sample/hustbookstore/admin/dashboard-view.fxml";
    }

    protected String getInventoryPath() {
        return "/sample/hustbookstore/admin/inventory-view.fxml";
    }

    protected String getStorePath() {
        return "/sample/hustbookstore/admin/store-view.fxml";
    }

    protected String getCustomersPath() {
        return "/sample/hustbookstore/admin/customers-view.fxml";
    }

    protected String getOthersPath() {
        return "/sample/hustbookstore/admin/others-view.fxml";
    }

    protected String getProfilePath() {
        return "/sample/hustbookstore/admin/profile-view.fxml";
    }

    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                Stage currentStage = (Stage) logout_btn.getScene().getWindow();
                currentStage.close();

                Parent root = FXMLLoader.load(getClass().getResource(getLaunchApplicationPath()));
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

    public void loadDashboard() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getDashboardPath()));
            dashboardScreen.getChildren().clear();
            dashboardScreen.getChildren().add(root);
            AnchorPane.setBottomAnchor(root, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        showHeaderAnimation();
    }

    public void loadInventory() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getInventoryPath()));
            inventoryScreen.getChildren().clear();
            inventoryScreen.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadStore() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getStorePath()));
            Platform.runLater(() -> {
                storeScreen.getChildren().clear();
                storeScreen.getChildren().add(root);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCustomers() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getCustomersPath()));
            customersScreen.getChildren().clear();
            customersScreen.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadOthers() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getOthersPath()));
            othersScreen.getChildren().clear();
            othersScreen.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProfile() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getProfilePath()));
            profileScreen.getChildren().clear();
            profileScreen.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showHeaderAnimation() {
        headerPane.setTranslateY(-70); // Ẩn header ban đầu (ra ngoài màn hình)
        headerPane.setVisible(true);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), headerPane);
        tt.setToY(-10); // Trượt xuống vị trí 0
        tt.play();
    }

    public void displayUsername() {
        String username = localAdmin.getUsername();
        this.username.setText(username);
        if (username.length() > 10) {
            this.username.setText(username.substring(0, 10) + "...");
        } else if (username.length() == 0) {
            this.username.setText("Not logged in");
        } else {
        }
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
    private Text waitingText;

    @FXML
    private FontAwesomeIcon syncIcon;


    @FXML
    public void handleSyncButtonAction(ActionEvent event) {
        if (event.getSource() == sync_btn) {

            waitingText.setVisible(true);
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), syncIcon);
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
            rotateTransition.play();

            // Chạy tác vụ tải trong luồng nền
            Task<Void> loadTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    // Thực hiện công việc nặng
                    loadStore();
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    Platform.runLater(() -> {
                        waitingText.setVisible(false);
                        rotateTransition.stop();
                    });
                }

                @Override
                protected void failed() {
                    super.failed();
                    Platform.runLater(() -> {
                        waitingText.setVisible(false);
                        rotateTransition.stop();
                        System.err.println("Failed to load store: " + getException().getMessage());
                    });
                }
            };

            new Thread(loadTask).start();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
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