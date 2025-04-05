package sample.hustbookstore;

import java.util.Date;

public class Stationery extends Product {

    public Stationery(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, Date addedDate) {
        super(ID, name, distributor, sellPrice, importPrice, stock, type, image, description, addedDate);
    }
}
