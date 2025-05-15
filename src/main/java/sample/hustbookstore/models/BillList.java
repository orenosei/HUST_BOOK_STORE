
package sample.hustbookstore.models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillList {
    private static Connection connect;

    public Bill prepareBill(int userId, List<CartItem> cartItems, float discount) {

        double totalPrice = 0;
        double totalProfit = 0;

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product != null) {
                double sellPrice = product.getSellPrice();
                double importPrice = product.getImportPrice();
                int quantity = item.getQuantity();

                totalPrice += sellPrice * quantity;
                totalProfit += (sellPrice - importPrice) * quantity;
            }
        }

        return new Bill(
                userId,
                totalPrice,
                totalProfit * (100 - discount) / 100,
                LocalDate.now()
        );
    }

    public boolean addBill(Bill bill) {
        String sql = "INSERT INTO bill (user_id, total_price, profit, purchase_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, bill.getUserID());
            statement.setDouble(2, bill.getTotalPrice());
            statement.setDouble(3, bill.getProfit());
            statement.setDate(4, Date.valueOf(bill.getPurchasedDate()));

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

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
