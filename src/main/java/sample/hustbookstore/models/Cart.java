package sample.hustbookstore.models;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import sample.hustbookstore.controllers.user.CartUpdateListener;
import sample.hustbookstore.utils.dao.CartList;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localCart;

public class Cart {

    private int cart_id;
    private int user_id;
    private ObservableList<CartItem> cartItemList;
    private float totalPrice;
    //private List<CartItem> selectedCartItems;

    private static CartUpdateListener listener;
    public static void setCartUpdateListener(CartUpdateListener cartListener) {
        listener = cartListener;
    }



    public Cart(){}

    public Cart(int cart_id, int user_id) {
        this.cart_id = cart_id;
        this.user_id = user_id;
    }

    public Cart(int cart_id, int user_id, ObservableList<CartItem> cartItemList, float totalPrice) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.cartItemList = cartItemList;
        this.totalPrice = totalPrice;
    }

    public int getCartId() {
        return cart_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ObservableList<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(ObservableList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean addProductToCart(String product_id, int quantity) {
        boolean success = CartList.addProduct(product_id, quantity, localCart.getCartId());
        if (success && listener != null) {
            Platform.runLater(listener::onCartUpdated);
        }
        return success;
    }

    public ObservableList<CartItem> getCartItemList(int cartId) {
        return CartList.getCartItemList(cartId);
    }

    public boolean updateCartItem(CartItem cartItem) {
        return CartList.updateCartItem(cartItem);
    }

    public boolean deleteCartItem(CartItem cartItem) {
        return CartList.deleteCartItem(cartItem);
    }

    public float calculateTotalPrice(int cartId) {
        return CartList.calculateTotalPrice(cartId);
    }

    public List<CartItem> getSelectedCartItems(int cartId) {
        return CartList.getSelectedCartItems(cartId);
    }

}
