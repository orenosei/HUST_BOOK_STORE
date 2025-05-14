package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.hustbookstore.controllers.admin.StoreUpdateListener;

import java.util.stream.Collectors;

import static sample.hustbookstore.LaunchApplication.localInventory;

public class Store{
    private final ObservableList<Book> bookListData = FXCollections.observableArrayList();
    private final ObservableList<Stationery> stationeryListData = FXCollections.observableArrayList();
    private final ObservableList<Toy> toyListData = FXCollections.observableArrayList();

    public ObservableList<Book> getBookListData() {
        // search
        // thay đổi bookListData dựa trên search

        return bookListData;
    }

    public ObservableList<Stationery> getStationeryListData() {
        return stationeryListData;
    }

    public ObservableList<Toy> getToyListData() {
        return toyListData;
    }

    public void refreshData() {
        bookListData.setAll(localInventory.getAllBooks()
                .stream()
                .filter(book -> book.getStock() > 0)
                .collect(Collectors.toList()));

        stationeryListData.setAll(localInventory.getAllStationery()
                .stream()
                .filter(stationery -> stationery.getStock() > 0)
                .collect(Collectors.toList()));

        toyListData.setAll(localInventory.getAllToys()
                .stream()
                .filter(toy -> toy.getStock() > 0)
                .collect(Collectors.toList()));
    }

}