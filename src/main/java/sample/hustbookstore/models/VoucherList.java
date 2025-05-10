package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class VoucherList {

    private static Connection connect;

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

    public static boolean updateVoucher(Voucher voucher) {
        String query = "UPDATE admin SET code=?, discount=?, duration=?, remaining=? WHERE voucher_id=?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, voucher.getCode());
            statement.setFloat(2, voucher.getDiscount());
            statement.setDate(3, (Date) voucher.getDuration());
            statement.setInt(4, voucher.getRemaining());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ObservableList<Voucher> getAllVouchers() {
        ObservableList<Voucher> voucherList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM voucher";

        try (PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                voucherList.add(new Voucher(
                        result.getString("code"),
                        result.getInt("remaining"),
                        result.getFloat("discount"),
                        result.getDate("duration"),
                        result.getInt("voucher_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voucherList;
    }

    public boolean isVoucherExists(int voucherID) {
        String query = "SELECT voucher_id FROM voucher WHERE voucher_id = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, voucherID);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean addVoucher(String code, int remaining, float discount, Date duration, int voucherID) {
        String insertQuery = "INSERT INTO product "
                + "(code, remaining, discount, duration, voucher_id)"
                + " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement prepare = connect.prepareStatement(insertQuery)) {
            prepare.setString(1, code);
            prepare.setInt(2, remaining);
            prepare.setFloat(3, discount);
            prepare.setDate(4, (Date) duration);
            prepare.setInt(5, voucherID);

            prepare.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(int voucherId) {
        String deleteQuery = "DELETE FROM voucher WHERE voucher_id = ?";

        try (PreparedStatement prepare = connect.prepareStatement(deleteQuery)) {
            prepare.setInt(1, voucherId);
            int rowsAffected = prepare.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
