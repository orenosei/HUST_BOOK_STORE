package sample.hustbookstore.models;

import static sample.hustbookstore.LaunchApplication.localInventory;

public class CartItem {
    private String productId;
    private int quantity;
    private boolean isSelected;
    private Product product;

    public CartItem(String productId, int quantity, boolean isSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.isSelected = isSelected;
        this.product = localInventory.getProductFromProductID(productId);
    }

    public Product getProduct() {
        return product;
    }
    public String getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }


}
