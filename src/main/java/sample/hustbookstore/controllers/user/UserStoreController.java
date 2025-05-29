package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.controllers.base.BaseStoreController;

import java.util.List;

public class UserStoreController extends BaseStoreController {

    public static AryaChatController arya;

    @Override
    protected String getRightPanelPath() {
        return "/sample/hustbookstore/user/arya-chat.fxml";
    }

    @Override
    protected String getProductCardPath() {
        return "/sample/hustbookstore/user/user-productCard-view.fxml";
    }

    @Override
    public void loadRightPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(getRightPanelPath()));
            AnchorPane pane = loader.load();
            arya = loader.getController();
            rightPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSearch() {
        String keyword = searchBar.getText().trim();
        if (keyword.isEmpty()) {
            resetAllGrids();
        } else {
            searchProducts(keyword);
        }
    }

    private void searchProducts(String keyword) {
        searchApplyToGridPane(tabBookGrid, allBookCards, keyword);
        searchApplyToGridPane(tabStationeryGrid, allStationeryCards, keyword);
        searchApplyToGridPane(tabToyGrid, allToyCards, keyword);
    }

    private void resetAllGrids() {
        resetGridPane(tabBookGrid, allBookCards);
        resetGridPane(tabStationeryGrid, allStationeryCards);
        resetGridPane(tabToyGrid, allToyCards);
    }

    @Override
    protected void onFilter() {
        List<String> genres = genreCheckComboBox.getCheckModel().getCheckedItems();
        int restrictedAge = parseAge();
        float[] priceRange = parsePriceRange();

        filterProducts(genres, restrictedAge, priceRange[0], priceRange[1]);
    }

    private void filterProducts(List<String> genres, int restrictedAge, float priceFrom, float priceTo) {
        filterApplyToGridPane(tabBookGrid, allBookCards, genres, restrictedAge, priceFrom, priceTo);
        filterApplyToGridPane(tabStationeryGrid, allStationeryCards, genres, restrictedAge, priceFrom, priceTo);
        filterApplyToGridPane(tabToyGrid, allToyCards, genres, restrictedAge, priceFrom, priceTo);
    }

    private int parseAge() {
        try {
            return Integer.parseInt(restrictAgeField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private float[] parsePriceRange() {
        float priceFrom = 0;
        float priceTo = Float.MAX_VALUE;

        try {
            priceFrom = Float.parseFloat(priceFromField.getText());
        } catch (NumberFormatException ignored) {}

        try {
            priceTo = Float.parseFloat(priceToField.getText());
        } catch (NumberFormatException ignored) {}

        return new float[]{priceFrom, priceTo};
    }

    @FXML
    public void initialize() {
        initializeCommon();
        loadRightPane();
        handleTabBook();
    }
}