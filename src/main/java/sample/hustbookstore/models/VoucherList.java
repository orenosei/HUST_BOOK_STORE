package sample.hustbookstore.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static sample.hustbookstore.LaunchApplication.localAdmin;

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
//    public VoucherList(List<Voucher> vouchers) {
//        this.vouchers = vouchers;
//    }

//    public int voucherCount() {
//        return vouchers.size();
//    }
//
//    public List<Voucher> getVouchers() {
//        // select * from voucher;
//        // vouchers = nhung gi lay duoc tu database
//        return vouchers;
//    }

    public void addVoucher(Voucher voucher) {}

    public void removeVoucher(Voucher voucher) {}

}
