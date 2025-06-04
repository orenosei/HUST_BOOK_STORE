package sample.hustbookstore.models;

public class BillItem {
    private Product product;
    private int quantity;
    private double priceAtPurchase;

    public BillItem(Product product, int quantity, double priceAtPurchase) {
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.product = product;
    }

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