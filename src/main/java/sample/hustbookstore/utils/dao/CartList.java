package sample.hustbookstore.utils.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.hustbookstore.models.Cart;
import sample.hustbookstore.models.CartItem;
import sample.hustbookstore.utils.cloud.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartList {

    private static Connection connect;

    public static boolean addProduct(String product_id, int quantity, int cartId ) {
        String checkQuery = "SELECT quantity FROM cart_item WHERE cart_id = ? AND product_id = ?";
        String stockQuery = "SELECT stock FROM product WHERE product_id = ?";
        String updateQuery = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product_id = ?";
        String insertQuery = "INSERT INTO cart_item (cart_id, product_id, quantity, is_selected) VALUES (?, ?, ?, false)";

        try {
            int currentQuantity = 0;
            int stock = 0;

            try (PreparedStatement checkStatement = connect.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, cartId);
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
                    updateStatement.setInt(2, cartId);
                    updateStatement.setString(3, product_id);
                    updateStatement.executeUpdate();
                    success = true;
                }
            } else {
                try (PreparedStatement insertStatement = connect.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, cartId);
                    insertStatement.setString(2, product_id);
                    insertStatement.setInt(3, quantity);
                    insertStatement.executeUpdate();
                    success = true;
                }
            }

            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Cart getCartFromDatabase(int user_id) {
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
                return cart;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<CartItem> getCartItemList(int cartId) {
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

    public static boolean updateCartItem(CartItem cartItem, int cartId) {
        String query = "UPDATE cart_item SET quantity = ?, is_selected = ? WHERE product_id = ? AND cart_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, cartItem.getQuantity());
            statement.setBoolean(2, cartItem.isSelected());
            statement.setString(3, cartItem.getProductId());
            statement.setInt(4, cartId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCartItem(CartItem cartItem, int cartId) {
        String query = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, cartId);
            statement.setString(2, cartItem.getProductId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }
}
