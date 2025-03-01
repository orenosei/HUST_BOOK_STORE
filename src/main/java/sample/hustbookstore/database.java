package sample.hustbookstore;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDB() {
        try {
            // Sử dụng driver mới thay vì driver đã bị khai tử
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Kết nối đến cơ sở dữ liệu với JDBC URL
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1qaz0okm");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}