package sample.hustbookstore.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.controllers.base.BaseHomeScreenController;
import sample.hustbookstore.controllers.base.StoreUpdateListener;

import java.net.URL;
import java.util.ResourceBundle;
import static sample.hustbookstore.LaunchApplication.*;

public class HomeScreenController extends BaseHomeScreenController implements Initializable, StoreUpdateListener {

    @FXML
    private Button customers_btn;
    @FXML
    private Button inventory_btn;
    @FXML
    private AnchorPane inventoryScreen;
    @FXML
    private AnchorPane customersScreen;


    @Override
    public String getDashboardPath() {
        return "/sample/hustbookstore/admin/dashboard-view.fxml";
    }

    public String getInventoryPath() {
        return "/sample/hustbookstore/admin/inventory-view.fxml";
    }

    @Override
    public String getStorePath() {
        return "/sample/hustbookstore/admin/store-view.fxml";
    }

    public String getCustomersPath() {
        return "/sample/hustbookstore/admin/customers-view.fxml";
    }

    @Override
    public String getOrderHistoryPath() {
        return "/sample/hustbookstore/admin/orderHistory-view.fxml";
    }

    @Override
    public String getProfilePath() {
        return "/sample/hustbookstore/admin/profile-view.fxml";
    }


    @Override
    public void onStoreUpdated() {
        reloadStore();
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

    public void loadCustomers() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getCustomersPath()));
            customersScreen.getChildren().clear();
            customersScreen.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void displayUsername() {
        String username = localAdmin.getUsername();
        if(username == null) username = "Admin";
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
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            showHeaderAnimation();

        } else if (event.getSource() == inventory_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(true);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);

        } else if (event.getSource() == store_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(true);
            headerPane.setVisible(false);

        } else if (event.getSource() == customers_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(true);
            othersScreen.setVisible(false);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);

        } else if (event.getSource() == others_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(true);
            profileScreen.setVisible(false);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);

        } else if (event.getSource() == profile_btn) {
            dashboardScreen.setVisible(false);
            inventoryScreen.setVisible(false);
            customersScreen.setVisible(false);
            othersScreen.setVisible(false);
            profileScreen.setVisible(true);
            storeScreen.setVisible(false);
            headerPane.setVisible(false);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InventoryController.setStoreUpdateListener(this);
        displayUsername();
        loadDashboard();
        dashboardScreen.setVisible(true);
        inventoryScreen.setVisible(false);
        customersScreen.setVisible(false);
        othersScreen.setVisible(false);
        profileScreen.setVisible(false);
        storeScreen.setVisible(false);

        loadInventory();
        loadStore();
        loadCustomers();
        loadOrderHistory();
        loadProfile();
    }

}