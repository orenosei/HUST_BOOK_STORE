package sample.hustbookstore.controllers.user;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.controllers.base.BaseHomeScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import static sample.hustbookstore.LaunchApplication.*;

public class UserHomeScreenController extends BaseHomeScreenController implements Initializable {

    @FXML
    private Button cart_btn;
    @FXML
    private AnchorPane cartScreen;


    protected String getCartPath() {
        return "/sample/hustbookstore/user/user-cart-view.fxml";
    }

    @Override
    protected String getDashboardPath() {
        return "/sample/hustbookstore/user/user-dashboard-view.fxml";
    }
    
    @Override
    protected String getStorePath() {
        return "/sample/hustbookstore/user/user-store-view.fxml";
    }
    
    @Override
    protected String getProfilePath() {
        return "/sample/hustbookstore/user/user-profile-view.fxml";
    }


    public void loadCart(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getCartPath()));
        try {
            AnchorPane root = loader.load();
            cartScreen.getChildren().clear();
            cartScreen.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserCartController controller = loader.getController();
        controller.setHomeScreenController(this);
    }

    @Override
    public void loadStore() {
        try {
            AnchorPane root = localUserStoreScreen;
            Platform.runLater(() -> {
                storeScreen.getChildren().clear();
                storeScreen.getChildren().add(root);
                System.out.println("Heloooo");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayUsername() {
        String username = localUser.getUsername();
        if(username == null) username = "User";
        this.username.setText(username);
        if (username.length() > 10) {
            this.username.setText(username.substring(0, 10) + "...");
        } else if (username.length() == 0) {
            this.username.setText("Not logged in");
        } else {
        }
    }

    @FXML
    @Override
    public void handleSidebarButtonAction(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboardScreen.setVisible(true);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            cartScreen.setVisible(false);
            showHeaderAnimation();

        } else if (event.getSource() == store_btn) {
            dashboardScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(true);
            headerPane.setVisible(false);
            cartScreen.setVisible(false);

        } else if (event.getSource() == profile_btn) {
            dashboardScreen.setVisible(false);
            profileScreen.setVisible(true);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);
            cartScreen.setVisible(false);

        }
        else if (event.getSource() == cart_btn) {
            dashboardScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            cartScreen.setVisible(true);
            headerPane.setVisible(false);
        }

    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        loadDashboard();
        dashboardScreen.setVisible(true);
        profileScreen.setVisible(false);
        storeScreen.setVisible(false);
        cartScreen.setVisible(false);

        loadStore();
        loadProfile();
        loadCart();
    }


}
