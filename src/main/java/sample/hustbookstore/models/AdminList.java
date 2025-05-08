//package sample.hustbookstore.models;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class AdminList {
//
//    private Connection connect;
//
//    public AdminList() {
//        connect = database.connectDB();
//    }
//
//    public boolean login(String username, String password) {
//        String query = "SELECT username, password FROM admin WHERE username = ? AND password = ?";
//        try (PreparedStatement statement = connect.prepareStatement(query)) {
//            statement.setString(1, username);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.next();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean isUsernameTaken(String username) {
//        String query = "SELECT username FROM admin WHERE username = ?";
//        try (PreparedStatement statement = connect.prepareStatement(query)) {
//            statement.setString(1, username);
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.next();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean registerAdmin(Admin admin) {
//        String query = "INSERT INTO admin (username, password, question, answer) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement statement = connect.prepareStatement(query)) {
//            statement.setString(1, admin.getUsername());
//            statement.setString(2, admin.getPassword());
//            statement.setString(3, admin.getQuestion());
//            statement.setString(4, admin.getAnswer());
//            statement.executeUpdate();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//}

package sample.hustbookstore.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static sample.hustbookstore.LaunchApplication.localAdmin;

public class AdminList {
    private static Connection connect;

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

    public static boolean login(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                localAdmin = new Admin(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        resultSet.getString("name"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("email"),
                        resultSet.getInt("admin_id")
                );
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUsernameTaken(String username) {
        String query = "SELECT username FROM admin WHERE username = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerAdmin(Admin admin) {
        String query = "INSERT INTO admin (username, password, question, answer, name, phonenumber, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, admin.getUsername());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getQuestion());
            statement.setString(4, admin.getAnswer());
            statement.setString(5, admin.getName());
            statement.setString(6, admin.getPhoneNumber());
            statement.setString(7, admin.getEmail());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAdmin(Admin admin) {
        String query = "UPDATE admin SET name=?, phonenumber=?, email=?, username=?, password=? WHERE admin_id=?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getPhoneNumber());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getUsername());
            statement.setString(5, admin.getPassword());
            statement.setInt(6, admin.getAdminId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection() {
        if (connect != null) {
            try {
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
