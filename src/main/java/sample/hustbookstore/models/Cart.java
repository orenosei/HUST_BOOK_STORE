package sample.hustbookstore.models;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.hustbookstore.controllers.user.CartUpdateListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localCart;

public class Cart {
    private static Connection connect;

    private int cart_id;
    private int user_id;

    public int getCartId() {
        return cart_id;
    }
    public Cart(){}

    public Cart(int cart_id, int user_id) {
        this.cart_id = cart_id;
        this.user_id = user_id;
    }


    private static CartUpdateListener listener;
    public static void setCartUpdateListener(CartUpdateListener cartListener) {
        listener = cartListener;
    }

    private static void notifyCartUpdated() {
        if (listener != null) {
            Platform.runLater(listener::onCartUpdated);
        }
    }


    public Cart getCartFromDatabase(int user_id) {
        Cart cart;

        String query = "SELECT * FROM cart WHERE user_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, user_id + "");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cart = new Cart(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("user_id")
                );
//                System.console().printf("Cart found for user_id: %d %d\n", cart.cart_id, cart.user_id);
                return cart;

            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addProductToCart(String product_id, int quantity) {
        String checkQuery = "SELECT quantity FROM cart_item WHERE cart_id = ? AND product_id = ?";
        String stockQuery = "SELECT stock FROM product WHERE product_id = ?";
        String updateQuery = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product_id = ?";
        String insertQuery = "INSERT INTO cart_item (cart_id, product_id, quantity, is_selected) VALUES (?, ?, ?, false)";

        try {
            int currentQuantity = 0;
            int stock = 0;

            try (PreparedStatement checkStatement = connect.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, localCart.getCartId());
                checkStatement.setString(2, product_id);
                ResultSet rs = checkStatement.executeQuery();
                if (rs.next()) {
                    currentQuantity = rs.getInt("quantity");
                }
            }

            try (PreparedStatement stockStatement = connect.prepareStatement(stockQuery)) {
                stockStatement.setString(1, product_id);
                ResultSet rs = stockStatement.executeQuery();
                if (rs.next()) {
                    stock = rs.getInt("stock");
                }
            }

            if (currentQuantity + quantity > stock) {
                System.out.println("Exceeds stock!");
                return false;
            }

            boolean success = false;

            if (currentQuantity > 0) {
                try (PreparedStatement updateStatement = connect.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, currentQuantity + quantity);
                    updateStatement.setInt(2, localCart.getCartId());
                    updateStatement.setString(3, product_id);
                    updateStatement.executeUpdate();
                    success = true;
                }
            } else {
                try (PreparedStatement insertStatement = connect.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, localCart.getCartId());
                    insertStatement.setString(2, product_id);
                    insertStatement.setInt(3, quantity);
                    insertStatement.executeUpdate();
                    success = true;
                }
            }

            // GỌI LISTENER SAU KHI THÀNH CÔNG
            if (success && listener != null) {
//                listener.onCartUpdated();
                Platform.runLater(listener::onCartUpdated);
            }

            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<CartItem> getCartItemList(int cartId) {
        ObservableList<CartItem> cartItemList = FXCollections.observableArrayList();
        String query = "SELECT * FROM cart_item WHERE cart_id = ?";

        try (PreparedStatement prepare = connect.prepareStatement(query)) {
            prepare.setInt(1, cartId);

            try (ResultSet result = prepare.executeQuery()) {
                while (result.next()) {
                    cartItemList.add(new CartItem(
                            result.getString("product_id"),
                            result.getInt("quantity"),
                            result.getBoolean("is_selected")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItemList;
    }

    public boolean updateCartItem(CartItem cartItem) {
        String query = "UPDATE cart_item SET quantity = ?, is_selected = ? WHERE product_id = ? AND cart_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, cartItem.getQuantity());
            statement.setBoolean(2, cartItem.isSelected());
            statement.setString(3, cartItem.getProductId());
            statement.setInt(4, localCart.getCartId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean deleteCartItem(CartItem cartItem) {
        String query = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, localCart.getCartId());
            statement.setString(2, cartItem.getProductId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public float calculateTotalPrice(int cartId) {
        String query = """
            SELECT SUM(p.sell_price * ci.quantity) AS total_price
            FROM cart_item ci
            JOIN product p ON ci.product_id = p.product_id
            WHERE ci.cart_id = ? AND ci.is_selected = true
    """;

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, cartId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getFloat("total_price");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

//    public List<CartItem> getSelectedCartItems() {
//        String query = """
//            SELECT * FROM cart_item
//            WHERE cart_id = ? AND is_selected = true
//        """;
//
//    }



    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }


}
