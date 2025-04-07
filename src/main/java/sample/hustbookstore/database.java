package sample.hustbookstore;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://hustbookstore.c38csaq64wfe.ap-southeast-2.rds.amazonaws.com:3306/bookstore";

            String user = "admin";
            String password = "1qaz0okm";

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
