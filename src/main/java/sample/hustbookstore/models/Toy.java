package sample.hustbookstore.models;

import java.time.LocalDate;

public class Toy extends Product {
    public Toy(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, LocalDate addedDate, int restrictedAge) {
        super(ID, name, distributor, sellPrice, importPrice, stock, type, image, description, addedDate, restrictedAge);
    }
}
