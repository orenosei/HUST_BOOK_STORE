
package sample.hustbookstore.models;

import javafx.scene.chart.XYChart;

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

    public static int countBill() {
        String sql = "SELECT COUNT(bill_id) FROM bill";
        try (PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static float calculateIncome(Date date1, Date date2) {
        String sql = "SELECT SUM(profit) FROM bill WHERE purchase_date BETWEEN ? AND ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setDate(1, date1);
            stmt.setDate(2, date2);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<XYChart.Data<String, Float>> getIncomeDataByDate() {
        List<XYChart.Data<String, Float>> dataList = new ArrayList<>();
        String sql = "SELECT purchase_date, SUM(profit) FROM bill GROUP BY purchase_date ORDER BY TIMESTAMP(purchase_date)";
        try (Connection conn = database.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                dataList.add(new XYChart.Data<>(rs.getString(1), rs.getFloat(2)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }


    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }


}
