package sample.hustbookstore;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // URL mới phù hợp với thông tin DigitalOcean
            String url = "jdbc:mysql://db-mysql-nyc3-64236-do-user-21926172-0.f.db.ondigitalocean.com:25060/bookstore?sslMode=REQUIRED";
            String user = "doadmin";
            String password = "AVNS_AtgscO0H_d3fHTjyx6d";

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
