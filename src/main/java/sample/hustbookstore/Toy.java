package sample.hustbookstore;

import java.util.Date;

public class Toy extends Product {

    public Toy(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, Date addedDate) {
        super(ID, name, distributor, sellPrice, importPrice, stock, type, image, description, addedDate);
    }
}
