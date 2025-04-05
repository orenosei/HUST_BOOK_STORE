package sample.hustbookstore;

import java.util.Date;

public class Product {
    private String ID;
    private String name;
    private String distributor;
    private Double sellPrice;
    private Double importPrice;
    private int stock;
    private String type;
    private String image;
    private String description;
    private Date addedDate;

    public Product(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, Date addedDate) {
        this.ID = ID;
        this.name = name;
        this.distributor = distributor;
        this.sellPrice = sellPrice;
        this.importPrice = importPrice;
        this.stock = stock;
        this.type = type;
        this.image = image;
        this.description = description;
        this.addedDate = addedDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Double importPrice) {
        this.importPrice = importPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
