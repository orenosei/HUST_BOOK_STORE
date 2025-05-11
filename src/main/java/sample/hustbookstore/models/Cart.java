package sample.hustbookstore.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static sample.hustbookstore.LaunchApplication.localUser;

public class Cart {
    private static Connection connect;

    int cart_id;
    int user_id;
    float total_cost;
    float final_price;

    public Cart(int cart_id, int user_id, float total_cost, float final_price) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.final_price = final_price;
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
                        resultSet.getInt("user_id"),
                        resultSet.getFloat("total_cost"),
                        resultSet.getFloat("final_price")
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






}
