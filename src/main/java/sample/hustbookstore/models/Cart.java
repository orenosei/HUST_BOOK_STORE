package sample.hustbookstore.models;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import sample.hustbookstore.controllers.user.CartUpdateListener;
import sample.hustbookstore.utils.dao.CartList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private int cartId;
    private User user;
    private ObservableList<CartItem> cartItemList;

    // giải thích hộ bố
    private static CartUpdateListener listener;

    public static void setCartUpdateListener(CartUpdateListener cartListener) {
        listener = cartListener;
    }
    // ///////////

    public Cart(){}

    public Cart(int cartId, User user, ObservableList<CartItem> cartItemList) {
        this.cartId = cartId;
        this.user = user;
        this.cartItemList = cartItemList;
    }

    public boolean addProductToCart(Product product, int quantity) {
        // check inventory
        if (product == null || product.getStock() < quantity) {
            return false;
        }

        // check gio hang xem da co chua
        Optional<CartItem> existingItem = cartItemList.stream()
                .filter(item -> item.getProduct().getID().equals(product.getID()))
                .findFirst();

        if (existingItem.isPresent()) {
            // neu co -> cap nhat so luong
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            item.setQuantity(newQuantity);
            if (listener != null) {
                Platform.runLater(listener::onCartUpdated);
            }
            return updateCartItem(item);
        } else {
            // neu chua -> them moi
            CartItem newItem = new CartItem(product, quantity, false);
            newItem.setProduct(product);

            boolean dbSuccess = CartList.addProduct(product, quantity, cartId);
            if (dbSuccess) {
                cartItemList.add(newItem);
                if (listener != null) {
                    Platform.runLater(listener::onCartUpdated);
                }
                return true;
            }
            return false;
        }
    }

    public CartItem findCartItem(String productId) {
        for (CartItem item : cartItemList) {
            if (item.getProduct().getID().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    public boolean updateCartItem(CartItem updatedItem) {
        CartItem existingItem = findCartItem(updatedItem.getProduct().getID());

        if (existingItem == null) {
            return false;
        }

        Product product = updatedItem.getProduct();
        if (product == null || product.getStock() < updatedItem.getQuantity()) {
            return false;
        }

        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setSelected(updatedItem.isSelected());

        boolean dbSuccess = CartList.updateCartItem(existingItem, cartId);

        if (dbSuccess) {
            calculateTotalPrice();
            if (listener != null) {
                Platform.runLater(listener::onCartUpdated);
            }
        }

        return dbSuccess;
    }

    public boolean deleteCartItem(CartItem cartItem) {
        boolean currentCartRemove = cartItemList.remove(cartItem);

        boolean dbSuccess = false;
        if (currentCartRemove) {
            dbSuccess = CartList.deleteCartItem(cartItem, cartId);
        }

        if (dbSuccess && listener != null) {
            Platform.runLater(listener::onCartUpdated);
        }

        return dbSuccess;
    }

    public float calculateTotalPrice() {
        float total = 0.0f;
        for (CartItem item : cartItemList) {
            if (item.isSelected() && item.getProduct() != null) {
                total += (float) (item.getProduct().getSellPrice() * item.getQuantity());
            }
        }
        return total;
    }

    public List<CartItem> getSelectedCartItems() {
        List<CartItem> selectedItems = new ArrayList<>();
        for (CartItem item : cartItemList) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public ObservableList<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(ObservableList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public float getTotalPrice() {
        return calculateTotalPrice();
    }

    public void setTotalPrice(float totalPrice) {
    }
}