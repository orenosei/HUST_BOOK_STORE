package sample.hustbookstore.controllers.user;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Stationery;
import sample.hustbookstore.models.Toy;

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


    public int[] updateRowColumn(int column, int row) {
        if (column == 2) {
            column = 0;
            row += 1;
        }
        return new int[]{column, row};
    }

    //public ObservableList<Book> bookListData = FXCollections.observableArrayList();
    public void tabBookDisplayCard() {
//        bookListData.clear();
        ObservableList<Book> bookListData = localStore.getBookListData();

        int row = 0;
        int column = 0;

        tabBookGrid.getRowConstraints().clear();
        tabBookGrid.getColumnConstraints().clear();

        for (int q = 0; q < bookListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource(getProductCardPath()));
                AnchorPane pane = load.load();
                UserStoreProductCardController cardC = load.getController();
                cardC.setBookData(bookListData.get(q));

                int[] updated = updateRowColumn(column, row);
                column = updated[0];
                row = updated[1];

                tabBookGrid.add(pane, column++, row);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //public ObservableList<Stationery> stationeryListData = FXCollections.observableArrayList();
    public void tabStationeryDisplayCard() {

        //stationeryListData.clear();
        ObservableList<Stationery> stationeryListData = localStore.getStationeryListData();

        int row = 0;
        int column = 0;

        tabStationeryGrid.getRowConstraints().clear();
        tabStationeryGrid.getColumnConstraints().clear();

        for (int q = 0; q < stationeryListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource(getProductCardPath()));
                AnchorPane pane = load.load();
                UserStoreProductCardController cardC = load.getController();
                cardC.setStationeryData(stationeryListData.get(q));

                int[] updated = updateRowColumn(column, row);
                column = updated[0];
                row = updated[1];

                tabStationeryGrid.add(pane, column++, row);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //private ObservableList<Toy> toyListData = FXCollections.observableArrayList();

    public void tabToyDisplayCard() {

        // toyListData.clear();
        ObservableList<Toy> toyListData = localStore.getToyListData();

        int row = 0;
        int column = 0;

        tabToyGrid.getRowConstraints().clear();
        tabToyGrid.getColumnConstraints().clear();

        for (int q = 0; q < toyListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource(getProductCardPath()));
                AnchorPane pane = load.load();
                UserStoreProductCardController cardC = load.getController();
                cardC.setToyData(toyListData.get(q));

                int[] updated = updateRowColumn(column, row);
                column = updated[0];
                row = updated[1];

                tabToyGrid.add(pane, column++, row);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        tabBookDisplayCard();
        tabStationeryDisplayCard();
        tabToyDisplayCard();
        loadRightPane();
    }
}
