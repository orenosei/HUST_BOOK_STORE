package sample.hustbookstore.models;

import java.time.LocalDate;

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
    private LocalDate addedDate;
    private int restrictedAge;
    private int sellQuantity;

    public Product(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, LocalDate addedDate, int restrictedAge, int sellQuantity) {
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
        this.restrictedAge = restrictedAge;
        this.sellQuantity = sellQuantity;
    }

    public Product(String name, String distributor, Double sellPrice, String type, String image, String description) {
        this.name = name;
        this.distributor = distributor;
        this.sellPrice = sellPrice;
        this.type = type;
        this.image = image;
        this.description = description;
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

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public int getRestrictedAge() {
        return restrictedAge;
    }

    public void setRestrictedAge(int restrictedAge) {
        this.restrictedAge = restrictedAge;
    }

    public int getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(int sellQuantity) {
        this.sellQuantity = sellQuantity;
    }
}
