package sample.hustbookstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StoreController {

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

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
    private GridPane tabBookGrid;

    @FXML
    private ScrollPane tabBookScroll;

    @FXML
    private GridPane tabStationeryGrid;

    @FXML
    private ScrollPane tabStationeryScroll;

    @FXML
    private GridPane tabToyGrid;

    @FXML
    private ScrollPane tabToyScroll;


    public int currentTab() {
        Tab selectedTab = tabStore.getSelectionModel().getSelectedItem();

        if (selectedTab == tabBook) {
            return 1;
        } else if (selectedTab == tabStationery) {
            return 2;
        } else if (selectedTab == tabToy) {
            return 3;
        } else {
            return 0;
        }
    }

    private ObservableList<Book> cardListData = FXCollections.observableArrayList();

    public ObservableList<Book> tabBookGetData() {

        String sql = "SELECT * FROM product";

        ObservableList<Book> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Book prod;

            while(result.next()) {
                prod = new Book(
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sellPrice"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description")
                );
                listData.add(prod);

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return listData;

    }

    public void tabBookDisplayCard() {

        cardListData.clear();
        cardListData.addAll(tabBookGetData());

        int row = 0;
        int column = 0;

        tabBookGrid.getRowConstraints().clear();
        tabBookGrid.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {

                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("productCard-view.fxml"));
                AnchorPane pane = load.load();
                StoreProductCardController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 2) {
                    column = 0;
                    row+=1;
                }

                tabBookGrid.add(pane, column++, row);


            } catch(Exception e) {
                e.printStackTrace();
            }

        }

    }



    public void initialize() {
        tabBookDisplayCard();
    }

}
