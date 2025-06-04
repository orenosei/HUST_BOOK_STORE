package sample.hustbookstore.models;

public class CartItem {
    private int quantity;
    private boolean isSelected;
    private Product product;

    public CartItem(Product product, int quantity, boolean isSelected) {
        this.quantity = quantity;
        this.isSelected = isSelected;
        this.product = product;
    }

    public Product getProduct() {
        return product;
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



}
