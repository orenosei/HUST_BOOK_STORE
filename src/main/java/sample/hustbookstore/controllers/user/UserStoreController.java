package sample.hustbookstore.controllers.user;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import sample.hustbookstore.models.*;

import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private CheckComboBox<String> genreCheckComboBox;

    @FXML
    private TextField priceFromField;

    @FXML
    private TextField priceToField;

    @FXML
    private TextField restrictAgeField;


    protected String getRightPanelPath(){
        return "/sample/hustbookstore/user/arya-chat.fxml";
    }

    public static AryaChatController arya;

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

    private void searchProducts(String keyword) {
        searchApplyToGridPane(tabBookGrid, allBookCards, keyword);
        searchApplyToGridPane(tabStationeryGrid, allStationeryCards, keyword);
        searchApplyToGridPane(tabToyGrid, allToyCards, keyword);
    }

    private void searchApplyToGridPane(GridPane gridPane, List<AnchorPane> allCards, String keyword) {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        for (AnchorPane card : allCards) {
            Object userData = card.getUserData();
            String productName = "";
            String description = "";
            String author = "";

            if (userData instanceof Book) {
                Book book = (Book) userData;
                productName = book.getName().toLowerCase();
                description = book.getDescription().toLowerCase();
                author = book.getAuthor().toLowerCase(); // Bổ sung thêm author
            } else if (userData instanceof Stationery) {
                Stationery stationery = (Stationery) userData;
                productName = stationery.getName().toLowerCase();
                description = stationery.getDescription().toLowerCase();
            } else if (userData instanceof Toy) {
                Toy toy = (Toy) userData;
                productName = toy.getName().toLowerCase();
                description = toy.getDescription().toLowerCase();
            }

            if (productName.contains(keyword.toLowerCase()) ||
                    description.contains(keyword.toLowerCase()) ||
                    author.contains(keyword.toLowerCase())) {
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
            searchProducts(keyword);
        }
    }

    public void loadMainPane() {
        clearGrids();
        tabBookDisplayCard();
        tabStationeryDisplayCard();
        tabToyDisplayCard();
    }

    public void setGenreList() {
        List<String> genres = new ArrayList<>();
        genres.add("Adventure");
        genres.add("Alternate History");
        genres.add("Autobiography");
        genres.add("Biography");
        genres.add("Business");
        genres.add("Children's Books");
        genres.add("Classic Literature");
        genres.add("Comedy");
        genres.add("Cooking");
        genres.add("Crime");
        genres.add("Cyberpunk");
        genres.add("Dark Fantasy");
        genres.add("Drama");
        genres.add("Dystopian");
        genres.add("Education");
        genres.add("Epic Fantasy");
        genres.add("Fantasy");
        genres.add("Gothic");
        genres.add("Graphic Novel");
        genres.add("Health & Wellness");
        genres.add("Historical");
        genres.add("Horror");
        genres.add("Light Novel");
        genres.add("LitRPG");
        genres.add("Magical Realism");
        genres.add("Manga");
        genres.add("Manhwa");
        genres.add("Martial Arts");
        genres.add("Memoir");
        genres.add("Mystery");
        genres.add("Mythology");
        genres.add("Philosophical");
        genres.add("Poetry");
        genres.add("Post-Apocalyptic");
        genres.add("Psychological");
        genres.add("Psychology");
        genres.add("Religious");
        genres.add("Romance");
        genres.add("Science");
        genres.add("Science Fiction");
        genres.add("Self-Help");
        genres.add("Slice of Life");
        genres.add("Space Opera");
        genres.add("Steampunk");
        genres.add("Technology");
        genres.add("Thriller");
        genres.add("Time Travel");
        genres.add("Travel");
        genres.add("Urban Fantasy");
        genres.add("War & Military");
        genres.add("Web Novel");
        genres.add("Young Adult");
        ObservableList<String> genreList = FXCollections.observableArrayList(genres);
        genreCheckComboBox.getItems().addAll(genreList);
    }
    private void handleTabBook() {
        Tab selectedTab = tabStore.getSelectionModel().getSelectedItem();
        boolean isBookTab = selectedTab == tabBook;
        genreCheckComboBox.setDisable(!isBookTab);
        if (!isBookTab) {
            genreCheckComboBox.getCheckModel().clearChecks();
        }
    }


    private void filterProducts(List<String> genres, int restrictedAge, float priceFrom, float priceTo) {
        filterApplyToGridPane(tabBookGrid, allBookCards, genres, restrictedAge, priceFrom, priceTo);
        filterApplyToGridPane(tabStationeryGrid, allStationeryCards, genres, restrictedAge, priceFrom, priceTo);
        filterApplyToGridPane(tabToyGrid, allToyCards, genres, restrictedAge, priceFrom, priceTo);
    }

    private void filterApplyToGridPane(GridPane gridPane, List<AnchorPane> allCards,
                                       List<String> genres, int restrictedAge,
                                       float priceFrom, float priceTo) {
        gridPane.getChildren().clear();

        int column = 0;
        int row = 0;
        for (AnchorPane card : allCards) {
            Object userData = card.getUserData();
            boolean showCard = true;

            if (userData instanceof Book) {
                Book book = (Book) userData;

                if (genres != null && !genres.isEmpty()) {
                    boolean genreMatch = false;
                    for (String genre : genres) {
                        if (book.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                            genreMatch = true;
                            break;
                        }
                    }
                    showCard = showCard && genreMatch;
                }
            }

            if (userData instanceof Product) {
                Product product = (Product) userData;
                if (product.getRestrictedAge() < restrictedAge) {
                    showCard = false;
                }
            }

            if (userData instanceof Product) {
                Product product = (Product) userData;
                double price = product.getSellPrice();
                if (price < priceFrom || price > priceTo) {
                    showCard = false;
                }
            }

            if (showCard) {
                gridPane.add(card, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    private void onFilter() {
        List<String> genres = genreCheckComboBox.getCheckModel().getCheckedItems();

        int restrictedAge = 0;
        try {
            restrictedAge = Integer.parseInt(restrictAgeField.getText());
        } catch (NumberFormatException _) {}

        float priceFrom = 0;
        float priceTo = Float.MAX_VALUE;
        try {
            priceFrom = Float.parseFloat(priceFromField.getText());
        } catch (NumberFormatException _) {}
        try {
            priceTo = Float.parseFloat(priceToField.getText());
        } catch (NumberFormatException _) {}

        filterProducts(genres, restrictedAge, priceFrom, priceTo);
    }

    private PauseTransition pause = new PauseTransition(Duration.millis(300));

    public void initialize() {

        store.refreshData();
        loadMainPane();
        loadRightPane();
        setGenreList();

        tabStore.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            handleTabBook();
        });
        handleTabBook();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {
                onSearch();
            });
            pause.stop();
            pause.playFromStart();
        });

        genreCheckComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            pause.setOnFinished(event -> {
                onFilter();
            });
            pause.stop();
            pause.playFromStart();
        });

        restrictAgeField.textProperty().addListener((obs, oldVal, newVal) -> {
            pause.setOnFinished(event -> {
                onFilter();
            });
            pause.stop();
            pause.playFromStart();

        });
        priceFromField.textProperty().addListener((obs, oldVal, newVal) -> {
            pause.setOnFinished(event -> {
                onFilter();
            });
            pause.stop();
            pause.playFromStart();
        });

        priceToField.textProperty().addListener((obs, oldVal, newVal) -> {
            pause.setOnFinished(event -> {
                onFilter();
            });
            pause.stop();
            pause.playFromStart();
        });
    }

}
