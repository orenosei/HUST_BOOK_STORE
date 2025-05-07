package sample.hustbookstore.models;

import java.time.LocalDate;

public class Stationery extends Product {

    public Stationery(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, LocalDate addedDate, int restrictedAge, int sellQuantity) {
        super(ID, name, distributor, sellPrice, importPrice, stock, type, image, description, addedDate, restrictedAge, sellQuantity);
    }

    public Stationery(String name, String distributor, Double sellPrice,String type, String image, String description) {
        super(name, distributor, sellPrice, type, image, description);
    }
}
