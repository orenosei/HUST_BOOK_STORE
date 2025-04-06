//package sample.hustbookstore;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class database {
//    public static Connection connectDB() {
//        try {
//            // Sử dụng driver mới thay vì driver đã bị khai tử
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // Kết nối đến cơ sở dữ liệu với JDBC URL
//            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1qaz0okm");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}

package sample.hustbookstore;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "hustbookstore.c38csaq64wfe.ap-southeast-2.rds.amazonaws.com"; //end point
            String user = "admin";
            String password = "1qaz0okm";

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
