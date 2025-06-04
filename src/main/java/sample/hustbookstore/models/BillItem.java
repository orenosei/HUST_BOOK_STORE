package sample.hustbookstore.models;

import sample.hustbookstore.utils.dao.Inventory;

public class BillItem {
    private String productId;
    private Product product;
    private int quantity;
    private double priceAtPurchase;

    public BillItem(String productId, int quantity, double priceAtPurchase) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.product = Inventory.getProductFromProductID(productId);
    }


    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(double priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public String getProductName() {
        return product.getName();
    }
}