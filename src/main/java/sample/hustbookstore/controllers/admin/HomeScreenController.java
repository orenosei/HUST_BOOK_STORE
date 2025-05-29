package sample.hustbookstore.controllers.admin;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.controllers.base.BaseHomeScreenController;
import java.net.URL;
import java.util.ResourceBundle;
import static sample.hustbookstore.LaunchApplication.*;

public class HomeScreenController extends BaseHomeScreenController implements Initializable, StoreUpdateListener  {

    @FXML
    private Button customers_btn;
    @FXML
    private Button others_btn;
    @FXML
    private Button inventory_btn;
    @FXML
    private AnchorPane inventoryScreen;
    @FXML
    private AnchorPane othersScreen;
    @FXML
    private AnchorPane customersScreen;


    @Override
    protected String getDashboardPath() {
        return "/sample/hustbookstore/admin/dashboard-view.fxml";
    }

    protected String getInventoryPath() {
        return "/sample/hustbookstore/admin/inventory-view.fxml";
    }

    @Override
    protected String getStorePath() {
        return "/sample/hustbookstore/admin/store-view.fxml";
    }

    protected String getCustomersPath() {
        return "/sample/hustbookstore/admin/customers-view.fxml";
    }

    protected String getOrderHistoryPath() {
        return "/sample/hustbookstore/admin/orderHistory-view.fxml";
    }

    @Override
    protected String getProfilePath() {
        return "/sample/hustbookstore/admin/profile-view.fxml";
    }


    @Override
    public void onStoreUpdated() {
        reloadStore();
    }

    public void loadInventory() {
        try {
            if (localInventoryScreen == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getInventoryPath()));
                localInventoryScreen = loader.load();
                localInventoryController = loader.getController();
            }

           // localInventoryController.setHomeScreenController(this);
            InventoryController.setStoreUpdateListener(this);

            inventoryScreen.getChildren().clear();
            inventoryScreen.getChildren().add(localInventoryScreen);
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

    public void loadOrderHistory() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(getOrderHistoryPath()));
            othersScreen.getChildren().clear();
            othersScreen.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadStore() {
        try {

            AnchorPane root = localStoreScreen;
            Platform.runLater(() -> {
                storeScreen.getChildren().clear();
                storeScreen.getChildren().add(root);
            });
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