package sample.hustbookstore.controllers.base;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

public abstract class BaseStoreController {
    @FXML
    protected AnchorPane rightPane;

    @FXML
    protected Tab tabBook;

    @FXML
    protected Tab tabStationery;

    @FXML
    protected TabPane tabStore;

    @FXML
    protected Tab tabToy;

    @FXML
    protected AnchorPane storeForm;

    @FXML
    public GridPane tabBookGrid;

    @FXML
    protected ScrollPane tabBookScroll;

    @FXML
    public GridPane tabStationeryGrid;

    @FXML
    protected ScrollPane tabStationeryScroll;

    @FXML
    protected GridPane tabToyGrid;

    @FXML
    protected ScrollPane tabToyScroll;

    @FXML
    protected TextField searchBar;

    @FXML
    protected CheckComboBox<String> genreCheckComboBox;

    @FXML
    protected TextField priceFromField;

    @FXML
    protected TextField priceToField;

    @FXML
    protected TextField restrictAgeField;


    protected abstract String getRightPanelPath();
    protected abstract String getProductCardPath();

    protected final Store store = new Store();
    protected final List<AnchorPane> allBookCards = new ArrayList<>();
    protected final List<AnchorPane> allStationeryCards = new ArrayList<>();
    protected final List<AnchorPane> allToyCards = new ArrayList<>();

    protected PauseTransition pause = new PauseTransition(Duration.millis(300));

    private void clearGrids() {
        tabBookGrid.getChildren().clear();
        tabStationeryGrid.getChildren().clear();
        tabToyGrid.getChildren().clear();
    }

    protected void displayProducts(GridPane grid, List<AnchorPane> cards, List<? extends Product> products) {
        cards.clear();
        grid.getChildren().clear();

        int column = 0;
        int row = 0;
        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(getProductCardPath()));
                AnchorPane pane = loader.load();
                ProductCardSetData controller = (ProductCardSetData) loader.getController();
                controller.setData(product);
                pane.setUserData(product);

                cards.add(pane);
                grid.add(pane, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void searchApplyToGridPane(GridPane gridPane, List<AnchorPane> allCards, String keyword) {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        for (AnchorPane card : allCards) {
            Object userData = card.getUserData();
            String searchData = getSearchData(userData).toLowerCase();

            if (searchData.contains(keyword.toLowerCase())) {
                gridPane.add(card, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    private String getSearchData(Object userData) {
        if (userData instanceof Book book) {
            return book.getName() + " " + book.getDescription() + " " + book.getAuthor();
        } else if (userData instanceof Product product) {
            return product.getName() + " " + product.getDescription();
        }
        return "";
    }

    protected void resetGridPane(GridPane gridPane, List<AnchorPane> allCards) {
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

    protected void setGenreList() {
        List<String> genres = List.of(
                "Adventure", "Alternate History", "Autobiography", "Biography", "Business",
                "Children's Books", "Classic Literature", "Comedy", "Cooking", "Crime",
                "Cyberpunk", "Dark Fantasy", "Drama", "Dystopian", "Education",
                "Epic Fantasy", "Fantasy", "Gothic", "Graphic Novel", "Health & Wellness",
                "Historical", "Horror", "Light Novel", "LitRPG", "Magical Realism",
                "Manga", "Manhwa", "Martial Arts", "Memoir", "Mystery",
                "Mythology", "Philosophical", "Poetry", "Post-Apocalyptic", "Psychological",
                "Psychology", "Religious", "Romance", "Science", "Science Fiction",
                "Self-Help", "Slice of Life", "Space Opera", "Steampunk", "Technology",
                "Thriller", "Time Travel", "Travel", "Urban Fantasy", "War & Military",
                "Web Novel", "Young Adult"
        );
        ObservableList<String> genreList = FXCollections.observableArrayList(genres);
        genreCheckComboBox.getItems().addAll(genreList);
    }

    protected void handleTabBook() {
        boolean isBookTab = tabStore.getSelectionModel().getSelectedItem() == tabBook;
        genreCheckComboBox.setDisable(!isBookTab);
        if (!isBookTab) genreCheckComboBox.getCheckModel().clearChecks();
    }

    protected void filterApplyToGridPane(GridPane gridPane, List<AnchorPane> allCards,
                                         List<String> genres, int restrictedAge,
                                         float priceFrom, float priceTo) {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;

        for (AnchorPane card : allCards) {
            Object userData = card.getUserData();
            if (shouldShowCard(userData, genres, restrictedAge, priceFrom, priceTo)) {
                gridPane.add(card, column++, row);
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    private boolean shouldShowCard(Object userData, List<String> genres,
                                   int restrictedAge, float priceFrom, float priceTo) {
        if (userData instanceof Book book) {
            return checkBookConditions(book, genres, restrictedAge, priceFrom, priceTo);
        } else if (userData instanceof Product product) {
            return checkGeneralConditions(product, restrictedAge, priceFrom, priceTo);
        }
        return false;
    }

    private boolean checkBookConditions(Book book, List<String> genres,
                                        int restrictedAge, float priceFrom, float priceTo) {
        boolean genreMatch = genres.isEmpty() || genres.stream()
                .anyMatch(genre -> book.getGenre().toLowerCase().contains(genre.toLowerCase()));

        return genreMatch &&
                book.getRestrictedAge() >= restrictedAge &&
                book.getSellPrice() >= priceFrom &&
                book.getSellPrice() <= priceTo;
    }

    private boolean checkGeneralConditions(Product product,
                                           int restrictedAge, float priceFrom, float priceTo) {
        return product.getRestrictedAge() >= restrictedAge &&
                product.getSellPrice() >= priceFrom &&
                product.getSellPrice() <= priceTo;
    }

    protected void initializeCommon() {
        store.refreshData();
        displayProducts(tabBookGrid, allBookCards, store.getBookListData());
        displayProducts(tabStationeryGrid, allStationeryCards, store.getStationeryListData());
        displayProducts(tabToyGrid, allToyCards, store.getToyListData());
        setGenreList();
        setupEventListeners();
    }

    private void setupEventListeners() {
        tabStore.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldTab, newTab) -> handleTabBook()
        );

        searchBar.textProperty().addListener((obs, oldVal, newVal) ->
                setupPauseTransition(this::onSearch)
        );

        genreCheckComboBox.getCheckModel().getCheckedItems().addListener(
                (ListChangeListener<String>) c -> setupPauseTransition(this::onFilter)
        );

        List.of(restrictAgeField, priceFromField, priceToField).forEach(field ->
                field.textProperty().addListener((obs, oldVal, newVal) ->
                        setupPauseTransition(this::onFilter)
                )
        );
    }

    private void setupPauseTransition(Runnable action) {
        pause.setOnFinished(e -> action.run());
        pause.playFromStart();
    }


    public abstract void loadRightPane();
    protected abstract void onSearch();
    protected abstract void onFilter();

}
