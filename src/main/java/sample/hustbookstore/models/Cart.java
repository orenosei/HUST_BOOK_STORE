package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static sample.hustbookstore.LaunchApplication.localCart;

public class Cart {
    private static Connection connect;

    private int cart_id;
    private int user_id;

    public int getCartId() {
        return cart_id;
    }

    public Cart(int cart_id, int user_id) {
        this.cart_id = cart_id;
        this.user_id = user_id;
    }

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
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
                //System.console().printf("Cart found for user_id: %d %d %f %f\n", cart.cart_id, cart.user_id, cart.total_cost, cart.final_price);
                return cart;

            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addProductToCart(String product_id, int quantity) {
        String query = "INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, localCart.cart_id);
            statement.setString(2, product_id);
            statement.setInt(3, quantity);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

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
                            false
                    ));
                }
            }
            System.out.println(cartId);
            System.out.println("Item test:");
            cartItemList.forEach(item -> {
                System.out.println("CartItem: Product ID = " + item.getProductId() +
                        ", Quantity = " + item.getQuantity() +
                        ", Selected = " + item.isSelected());
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItemList;
    }

    public static boolean updateProductQuantity(String productId, int quantity) {
        String query = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, localCart.cart_id);
            statement.setString(3, productId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteProductFromCart(String productId) {
        String query = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, localCart.cart_id);
            statement.setString(2, productId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
