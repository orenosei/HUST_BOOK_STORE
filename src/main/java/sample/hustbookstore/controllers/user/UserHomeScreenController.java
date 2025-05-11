package sample.hustbookstore.controllers.user;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.hustbookstore.controllers.admin.HomeScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class UserHomeScreenController extends HomeScreenController {

    @FXML
    private AnchorPane cartScreen;

    @FXML
    private Button cart_btn;

    @FXML
    private AnchorPane dashboardScreen;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane headerPane;

    @FXML
    private AnchorPane home_screen;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane profileScreen;

    @FXML
    private Button profile_btn;

    @FXML
    private AnchorPane storeScreen;

    @FXML
    private Button store_btn;

    @FXML
    private FontAwesomeIcon syncIcon;

    @FXML
    private Button sync_btn;

    @FXML
    private Text username;

    @FXML
    private Text waitingText;

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


    public void loadCart() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getCartPath()));
            cartScreen.getChildren().clear();
            cartScreen.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSidebarButtonAction(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboardScreen.setVisible(true);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            cartScreen.setVisible(false);
            sync_btn.setVisible(false);
            showHeaderAnimation();


        } else if (event.getSource() == store_btn) {
            dashboardScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(true);
            headerPane.setVisible(false);
            cartScreen.setVisible(false);
            sync_btn.setVisible(true);


        } else if (event.getSource() == profile_btn) {
            dashboardScreen.setVisible(false);
            profileScreen.setVisible(true);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);
            cartScreen.setVisible(false);
            sync_btn.setVisible(false);

        }
        else if (event.getSource() == cart_btn) {
            dashboardScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            cartScreen.setVisible(true);
            sync_btn.setVisible(false);
            headerPane.setVisible(false);


        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //displayUsername();
        loadDashboard();
        dashboardScreen.setVisible(true);
        profileScreen.setVisible(false);
        storeScreen.setVisible(false);
        cartScreen.setVisible(false);
//        sync_btn.setVisible(false);
        //headerPane.setVisible(true);

        loadStore();
        loadProfile();
        loadCart();
    }


}
