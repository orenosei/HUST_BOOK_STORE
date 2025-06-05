package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.hustbookstore.utils.dao.InventoryDAO;
import java.util.stream.Collectors;

public class Store {
    private ObservableList<Book> bookListData = FXCollections.observableArrayList();
    private ObservableList<Stationery> stationeryListData = FXCollections.observableArrayList();
    private ObservableList<Toy> toyListData = FXCollections.observableArrayList();

    public void refreshData() {
        bookListData.setAll(InventoryDAO.getAllBooks()
                .stream()
                .filter(book -> book.getStock() > 0)
                .collect(Collectors.toList()));

        stationeryListData.setAll(InventoryDAO.getAllStationery()
                .stream()
                .filter(stationery -> stationery.getStock() > 0)
                .collect(Collectors.toList()));

        toyListData.setAll(InventoryDAO.getAllToys()
                .stream()
                .filter(toy -> toy.getStock() > 0)
                .collect(Collectors.toList()));
    }


    public ObservableList<Book> getBookListData() { return bookListData; }
    public ObservableList<Stationery> getStationeryListData() { return stationeryListData; }
    public ObservableList<Toy> getToyListData() { return toyListData; }
}