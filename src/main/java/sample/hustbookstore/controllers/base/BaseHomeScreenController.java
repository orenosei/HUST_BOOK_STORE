package sample.hustbookstore.controllers.base;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Optional;

public abstract class BaseHomeScreenController implements Initializable{

    @FXML
    protected AnchorPane home_screen;
    @FXML protected Button logout_btn;
    @FXML protected Button profile_btn;
    @FXML protected Button store_btn;
    @FXML protected Text username;
    @FXML protected AnchorPane headerPane;
    @FXML protected AnchorPane dashboardScreen;
    @FXML protected AnchorPane storeScreen;
    @FXML protected AnchorPane profileScreen;
    @FXML protected Button sync_btn;
    @FXML protected Button dashboard_btn;
    @FXML protected FontAwesomeIcon syncIcon;

    protected Alert alert;

    public abstract void displayUsername();
    public abstract String getDashboardPath();
    public abstract String getStorePath();
    public abstract String getProfilePath();

    @FXML
    public abstract void handleSidebarButtonAction(ActionEvent event);

    public String getLaunchApplicationPath() {
        return "/sample/hustbookstore/LaunchApplication.fxml";
    }

    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get() == ButtonType.OK) {
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

    public void loadProfile() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getProfilePath()));
            profileScreen.getChildren().clear();
            profileScreen.getChildren().add(root);

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


    @FXML
    public void handleSyncButtonAction(ActionEvent event) {
        if (event.getSource() == sync_btn) {
            reloadStore();
        }
    }

    public void reloadStore() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), syncIcon);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.play();

        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() {
                loadStore();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(rotateTransition::stop);
            }

            @Override
            protected void failed() {
                super.failed();
                Platform.runLater(() -> {
                    rotateTransition.stop();
                    System.err.println("Failed to load store: " + getException().getMessage());
                });
            }
        };

        new Thread(loadTask).start();
    }

    public void showHeaderAnimation() {
        headerPane.setTranslateY(-70);
        headerPane.setVisible(true);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), headerPane);
        tt.setToY(-10);
        tt.play();
    }

}