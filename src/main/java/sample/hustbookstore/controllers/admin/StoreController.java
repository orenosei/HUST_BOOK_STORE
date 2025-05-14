package sample.hustbookstore.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Stationery;
import sample.hustbookstore.models.Store;
import sample.hustbookstore.models.Toy;

import static sample.hustbookstore.LaunchApplication.localStore;

public class StoreController {

    @FXML
    private Tab tabBook;

    @FXML
    private Tab tabStationery;

    @FXML
    private TabPane tabStore;

    @FXML
    private Tab tabToy;

    @FXML
    private Button codeAddButton;

    @FXML
    private TableColumn<?, ?> codeAvailablecodeCol;

    @FXML
    private Button codeClearButton;

    @FXML
    private TextField codeCodeField;

    @FXML
    private Button codeDeleteallButton;

    @FXML
    private Button codeDeleteselectedButton;

    @FXML
    private TableColumn<?, ?> codeDiscountCol;

    @FXML
    private TextField codeDiscountField;

    @FXML
    private TableColumn<?, ?> codeDurationCol;

    @FXML
    private TextField codeDurationField;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productNewpriceCol;

    @FXML
    private TableColumn<?, ?> productOriginalpriceCol;

    @FXML
    private AnchorPane storeForm;

    @FXML
    public GridPane tabBookGrid;

    @FXML
    private ScrollPane tabBookScroll;

    @FXML
    public GridPane tabStationeryGrid;

    @FXML
    private ScrollPane tabStationeryScroll;

    @FXML
    private GridPane tabToyGrid;

    @FXML
    private ScrollPane tabToyScroll;
    @FXML
    private AnchorPane rightPane;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchBtn;




    protected String getRightPanelPath() {
        return "/sample/hustbookstore/admin/voucher-view.fxml";
    }

    protected String getProductCardPath() {
        return "/sample/hustbookstore/admin/productCard-view.fxml";
    }
    

    public void loadRightPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(getRightPanelPath()));
            AnchorPane pane = loader.load();
            rightPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Store store = new Store();

    private void clearGrids() {
        tabBookGrid.getChildren().clear();
        tabStationeryGrid.getChildren().clear();
        tabToyGrid.getChildren().clear();
    }

    private void tabBookDisplayCard() {
        int column = 0;
        int row = 0;
        for (Book book : store.getBookListData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                StoreProductCardController controller = loader.getController();
                controller.setData(book);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                tabBookGrid.add(pane, column++, row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void tabStationeryDisplayCard() {
        int column = 0;
        int row = 0;
        for (Stationery stationery : store.getStationeryListData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                StoreProductCardController controller = loader.getController();
                controller.setData(stationery);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                tabStationeryGrid.add(pane, column++, row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void tabToyDisplayCard() {
        int column = 0;
        int row = 0;
        for (Toy toy : store.getToyListData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                StoreProductCardController controller = loader.getController();
                controller.setData(toy);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                tabToyGrid.add(pane, column++, row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onSearch() {
        String keyword = searchBar.getText().trim();
        if (keyword.isEmpty()) {
            store.refreshData();
        } else {
            store.filterProducts(keyword);
        }
        loadMainPane();
    }

    public void loadMainPane() {
        //store.refreshData();
        clearGrids();
        tabBookDisplayCard();
        tabStationeryDisplayCard();
        tabToyDisplayCard();
    }


    public void initialize() {
        store.refreshData();
        loadMainPane();
        loadRightPane();
    }

}