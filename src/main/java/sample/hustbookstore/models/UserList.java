package sample.hustbookstore.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static sample.hustbookstore.LaunchApplication.localCart;
import static sample.hustbookstore.LaunchApplication.localUser;

public class UserList {
    private static Connection connect;

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

    public static boolean login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                localUser = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        resultSet.getString("name"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getInt("user_id")
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
        String query = "SELECT username FROM user WHERE username = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(User user) {
        String query = "INSERT INTO user (username, password, question, answer, name, phonenumber, email, address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getQuestion());
            statement.setString(4, user.getAnswer());
            statement.setString(5, user.getName());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getEmail());
            statement.setString(8, user.getAddress());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(User user) {
        String query = "UPDATE user SET name=?, phonenumber=?, email=?, username=?, password=?, address=? WHERE user_id=?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPhoneNumber());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getAddress());
            statement.setInt(7, user.getUserId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifySecurityInfo(String username, String question, String answer) {
        String sql = "SELECT 1 FROM user WHERE username = ? AND question = ? AND answer = ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, question);
            stmt.setString(3, answer);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int countUser() {
        String sql = "SELECT COUNT(user_id) FROM user";
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