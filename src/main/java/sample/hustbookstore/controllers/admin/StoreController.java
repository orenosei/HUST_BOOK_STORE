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
import sample.hustbookstore.models.Toy;
import sample.hustbookstore.models.database;

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

    public ObservableList<Book> bookListData = FXCollections.observableArrayList();

    public int[] updateRowColumn(int column, int row) {
        if (column == 2) {
            column = 0;
            row += 1;
        }
        return new int[]{column, row};
    }

    public ObservableList<Book> tabBookGetData() {

        String sql = "SELECT * FROM product where type = 'Book'";

        ObservableList<Book> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Book prod;

            while (result.next()) {
                prod = new Book(
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description")
                );
                listData.add(prod);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }

    public void tabBookDisplayCard() {

        bookListData.clear();
        bookListData.addAll(tabBookGetData());

        int row = 0;
        int column = 0;

        tabBookGrid.getRowConstraints().clear();
        tabBookGrid.getColumnConstraints().clear();

        for (int q = 0; q < bookListData.size(); q++) {

            try {

                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/sample/hustbookstore/admin/productCard-view.fxml"));
                AnchorPane pane = load.load();
                StoreProductCardController cardC = load.getController();
                cardC.setData(bookListData.get(q));

                int[] updated = updateRowColumn(column, row);
                column = updated[0];
                row = updated[1];

                tabBookGrid.add(pane, column++, row);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public ObservableList<Stationery> stationeryListData = FXCollections.observableArrayList();

    public ObservableList<Stationery> tabStationeryGetData() {

        String sql = "SELECT * FROM product where type = 'Stationery'";

        ObservableList<Stationery> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Stationery prod;

            while (result.next()) {
                prod = new Stationery(
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description")
                );
                listData.add(prod);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }

    public void tabStationeryDisplayCard() {

        stationeryListData.clear();
        stationeryListData.addAll(tabStationeryGetData());

        int row = 0;
        int column = 0;

        tabStationeryGrid.getRowConstraints().clear();
        tabStationeryGrid.getColumnConstraints().clear();

        for (int q = 0; q < stationeryListData.size(); q++) {

            try {

                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/sample/hustbookstore/admin/productCard-view.fxml"));
                AnchorPane pane = load.load();
                StoreProductCardController cardC = load.getController();
                cardC.setData(stationeryListData.get(q));

                int[] updated = updateRowColumn(column, row);
                column = updated[0];
                row = updated[1];

                tabStationeryGrid.add(pane, column++, row);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private ObservableList<Toy> toyListData = FXCollections.observableArrayList();

    public ObservableList<Toy> tabToyGetData() {

        String sql = "SELECT * FROM product where type = 'Toy'";

        ObservableList<Toy> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Toy prod;

            while (result.next()) {
                prod = new Toy(
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description")
                );
                listData.add(prod);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;

    }

    public void tabToyDisplayCard() {

        toyListData.clear();
        toyListData.addAll(tabToyGetData());

        int row = 0;
        int column = 0;

        tabToyGrid.getRowConstraints().clear();
        tabToyGrid.getColumnConstraints().clear();

        for (int q = 0; q < toyListData.size(); q++) {

            try {

                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/sample/hustbookstore/admin/productCard-view.fxml"));
                AnchorPane pane = load.load();
                StoreProductCardController cardC = load.getController();
                cardC.setData(toyListData.get(q));

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
    }

}