package sample.hustbookstore.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.controllers.base.BaseStoreController;

import java.util.List;

public class StoreController extends BaseStoreController {

    @Override
    public String getRightPanelPath() {
        return "/sample/hustbookstore/admin/voucher-view.fxml";
    }

    @Override
    public String getProductCardPath() {
        return "/sample/hustbookstore/admin/productCard-view.fxml";
    }

    @Override
    public void loadRightPane() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(getRightPanelPath()));
            rightPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearch() {
        String keyword = searchBar.getText().trim();
        if (keyword.isEmpty()) {
            resetAllGrids();
        } else {
            searchProducts(keyword);
        }
    }

    public void searchProducts(String keyword) {
        searchApplyToGridPane(tabBookGrid, allBookCards, keyword);
        searchApplyToGridPane(tabStationeryGrid, allStationeryCards, keyword);
        searchApplyToGridPane(tabToyGrid, allToyCards, keyword);
    }

    public void resetAllGrids() {
        resetGridPane(tabBookGrid, allBookCards);
        resetGridPane(tabStationeryGrid, allStationeryCards);
        resetGridPane(tabToyGrid, allToyCards);
    }

    @Override
    public void onFilter() {
        List<String> genres = genreCheckComboBox.getCheckModel().getCheckedItems();
        int restrictedAge = parseAge();
        float[] priceRange = parsePriceRange();

        filterProducts(genres, restrictedAge, priceRange[0], priceRange[1]);
    }

    public void filterProducts(List<String> genres, int restrictedAge, float priceFrom, float priceTo) {
        filterApplyToGridPane(tabBookGrid, allBookCards, genres, restrictedAge, priceFrom, priceTo);
        filterApplyToGridPane(tabStationeryGrid, allStationeryCards, genres, restrictedAge, priceFrom, priceTo);
        filterApplyToGridPane(tabToyGrid, allToyCards, genres, restrictedAge, priceFrom, priceTo);
    }

    public int parseAge() {
        try {
            return Integer.parseInt(restrictAgeField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public float[] parsePriceRange() {
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