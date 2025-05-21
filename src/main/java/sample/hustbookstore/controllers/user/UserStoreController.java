package sample.hustbookstore.controllers.user;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.hustbookstore.controllers.admin.StoreProductCardController;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Stationery;
import sample.hustbookstore.models.Store;
import sample.hustbookstore.models.Toy;

import java.util.ArrayList;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localStore;

public class UserStoreController{

    @FXML
    private AnchorPane rightPane;

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
    private TextField searchBar;


    protected String getRightPanelPath(){
        return "/sample/hustbookstore/user/arya-chat.fxml";
    }


    protected String getProductCardPath() {
        return "/sample/hustbookstore/user/user-productCard-view.fxml";
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

    private final List<AnchorPane> allBookCards = new ArrayList<>();
    private final List<AnchorPane> allStationeryCards = new ArrayList<>();
    private final List<AnchorPane> allToyCards = new ArrayList<>();

    private void clearGrids() {
        tabBookGrid.getChildren().clear();
        tabStationeryGrid.getChildren().clear();
        tabToyGrid.getChildren().clear();
    }


    private void tabBookDisplayCard() {
        allBookCards.clear();
        tabBookGrid.getChildren().clear();

        int column = 0;
        int row = 0;
        for (Book book : store.getBookListData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                UserStoreProductCardController controller = loader.getController();
                controller.setData(book);
                pane.setUserData(book);

                allBookCards.add(pane);
                tabBookGrid.add(pane, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void tabToyDisplayCard() {
        allToyCards.clear();
        tabToyGrid.getChildren().clear();

        int column = 0;
        int row = 0;
        for (Toy toy : store.getToyListData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                UserStoreProductCardController controller = loader.getController();
                controller.setData(toy);
                pane.setUserData(toy);


                allToyCards.add(pane);
                tabToyGrid.add(pane, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void tabStationeryDisplayCard() {
        allStationeryCards.clear();
        tabStationeryGrid.getChildren().clear();

        int column = 0;
        int row = 0;
        for (Stationery stationery : store.getStationeryListData()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                UserStoreProductCardController controller = loader.getController();
                controller.setData(stationery);
                pane.setUserData(stationery);

                allStationeryCards.add(pane);
                tabStationeryGrid.add(pane, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void filterProducts(String keyword) {
        filterGridPane(tabBookGrid, allBookCards, keyword);
        filterGridPane(tabStationeryGrid, allStationeryCards, keyword);
        filterGridPane(tabToyGrid, allToyCards, keyword);
    }

    private void filterGridPane(GridPane gridPane, List<AnchorPane> allCards, String keyword) {
        gridPane.getChildren().clear();

        int column = 0;
        int row = 0;
        for (AnchorPane card : allCards) {
            Object userData = card.getUserData();
            String productName = "";

            if (userData instanceof Book) {
                productName = ((Book) userData).getName().toLowerCase();
            } else if (userData instanceof Stationery) {
                productName = ((Stationery) userData).getName().toLowerCase();
            } else if (userData instanceof Toy) {
                productName = ((Toy) userData).getName().toLowerCase();
            }

            if (productName.contains(keyword.toLowerCase())) {
                gridPane.add(card, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        }
    }


    private void resetGridPane(GridPane gridPane, List<AnchorPane> allCards) {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        for (AnchorPane card : allCards) {
            gridPane.add(card, column++, row);
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }

    @FXML
    private void onSearch() {
        String keyword = searchBar.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            resetGridPane(tabBookGrid, allBookCards);
            resetGridPane(tabStationeryGrid, allStationeryCards);
            resetGridPane(tabToyGrid, allToyCards);
        } else {
            filterProducts(keyword);
        }
    }

    public void loadMainPane() {

        clearGrids();
        tabBookDisplayCard();
        tabStationeryDisplayCard();
        tabToyDisplayCard();
    }


    public void initialize() {
        store.refreshData();
        loadMainPane();
        loadRightPane();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch();
        });
    }

}
