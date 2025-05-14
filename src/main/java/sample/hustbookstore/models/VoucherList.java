package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class VoucherList {

    private static Connection connect;

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

    public static boolean updateVoucher(String code, int remaining, float discount, LocalDate duration, int voucher_id) {
        String updateQuery = "UPDATE voucher "
                + "SET code=?, remaining=?, discount=?, duration=?"
                + "WHERE voucher_id=?";
        try (PreparedStatement prepare = connect.prepareStatement(updateQuery)) {
            prepare.setString(1, code);
            prepare.setInt(2, remaining);
            prepare.setFloat(3, discount);
            prepare.setDate(4, java.sql.Date.valueOf(duration));
            prepare.setInt(5, voucher_id);

            int rowsAffected = prepare.executeUpdate();
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
                        result.getDate("duration").toLocalDate(),
                        result.getInt("voucher_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voucherList;
    }

    public boolean isVoucherExists(String voucherCode) {
        String query = "SELECT code FROM voucher WHERE code = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, voucherCode);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean addVoucher(String code, int remaining, float discount, Date duration) {
        String insertQuery = "INSERT INTO voucher "
                + "(code, remaining, discount, duration)"
                + " VALUES (?, ?, ?, ?)";

        try (PreparedStatement prepare = connect.prepareStatement(insertQuery)) {
            prepare.setString(1, code);
            prepare.setInt(2, remaining);
            prepare.setFloat(3, discount);
            prepare.setDate(4, (Date) duration);


            prepare.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVoucher(String voucherCode) {
        String deleteQuery = "DELETE FROM voucher WHERE code = ?";

        try (PreparedStatement prepare = connect.prepareStatement(deleteQuery)) {
            prepare.setString(1, voucherCode);
            int rowsAffected = prepare.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Voucher getVoucher(String voucherCode) {
        String query = "SELECT * FROM voucher WHERE code = ?";
        try (PreparedStatement prepare = connect.prepareStatement(query)) {
            prepare.setString(1, voucherCode);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                Voucher voucher = new Voucher(
                        result.getString("code"),
                        result.getInt("remaining"),
                        result.getFloat("discount"),
                        result.getDate("duration").toLocalDate(),
                        result.getInt("voucher_id")
                );
                return voucher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
