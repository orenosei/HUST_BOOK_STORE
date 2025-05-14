package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

import static sample.hustbookstore.LaunchApplication.localInventory;

public class Store {
    private final ObservableList<Book> bookListData = FXCollections.observableArrayList();
    private final ObservableList<Stationery> stationeryListData = FXCollections.observableArrayList();
    private final ObservableList<Toy> toyListData = FXCollections.observableArrayList();

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


    public void filterProducts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();

        bookListData.setAll(localInventory.getAllBooks().stream()
                .filter(book -> book.getStock() > 0 && book.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList()));

        stationeryListData.setAll(localInventory.getAllStationery().stream()
                .filter(stationery -> stationery.getStock() > 0 && stationery.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList()));

        toyListData.setAll(localInventory.getAllToys().stream()
                .filter(toy -> toy.getStock() > 0 && toy.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList()));
    }


    public ObservableList<Book> getBookListData() { return bookListData; }
    public ObservableList<Stationery> getStationeryListData() { return stationeryListData; }
    public ObservableList<Toy> getToyListData() { return toyListData; }
}