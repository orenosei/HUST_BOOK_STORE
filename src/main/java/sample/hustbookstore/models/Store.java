package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static sample.hustbookstore.LaunchApplication.localInventory;

public class Store {
    private static ObservableList<Book> bookListData = FXCollections.observableArrayList();
    private static ObservableList<Stationery> stationeryListData = FXCollections.observableArrayList();
    private static ObservableList<Toy> toyListData = FXCollections.observableArrayList();


    public ObservableList<Book> getBookListData() {
        return bookListData;
    }

    public ObservableList<Stationery> getStationeryListData() {
        return stationeryListData;
    }

    public ObservableList<Toy> getToyListData() {
        return toyListData;
    }

    public static void initialize() {
        bookListData.clear();
        stationeryListData.clear();
        toyListData.clear();

        // Filter > 0
        localInventory.getAllBooks().stream()
                .filter(book -> book.getStock() > 0)
                .forEach(bookListData::add);
        localInventory.getAllStationery().stream()
                .filter(stationery -> stationery.getStock() > 0)
                .forEach(stationeryListData::add);
        localInventory.getAllToys().stream()
                .filter(toy -> toy.getStock() > 0)
                .forEach(toyListData::add);
    }
}