package sample.hustbookstore.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.hustbookstore.utils.cloud.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Inventory {
    private static Connection connect;

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

    public ObservableList<Book> getAllProducts() {
        ObservableList<Book> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";

        try (PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                productList.add(new Book(
                        result.getString("product_id"),
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getDouble("import_price"),
                        result.getInt("stock"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description"),
                        result.getDate("added_date") != null ? result.getDate("added_date").toLocalDate() : null,
                        result.getInt("age_restrict"),
                        result.getString("isbn"),
                        result.getString("genre"),
                        result.getDate("pub_date") != null ? result.getDate("pub_date").toLocalDate() : null,
                        result.getString("author")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public boolean isProductExists(String productId) {
        String query = "SELECT product_id FROM product WHERE product_id = ?";
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, productId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProduct(String ID, String name, String distributor, Double sellPrice,
                              Double importPrice, int stock, String type, String image, String description,
                              LocalDate addedDate, int restrictedAge, String isbn, String genre, LocalDate publishedDate, String author) {
        String insertQuery = "INSERT INTO product "
                + "(product_id, type, name, image, distributor, description, added_date, stock, import_price, sell_price, age_restrict, isbn, author, genre, pub_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement prepare = connect.prepareStatement(insertQuery)) {
            prepare.setString(1, ID);
            prepare.setString(2, type);
            prepare.setString(3, name);
            prepare.setString(4, image);
            prepare.setString(5, distributor);
            prepare.setString(6, description);
            prepare.setDate(7, java.sql.Date.valueOf(addedDate));
            prepare.setInt(8, stock);
            prepare.setDouble(9, importPrice);
            prepare.setDouble(10, sellPrice);
            prepare.setInt(11, restrictedAge);

            if ("Book".equals(type)) {
                prepare.setString(12, isbn);
                prepare.setString(13, author);
                prepare.setString(14, genre);
                prepare.setDate(15, publishedDate != null ? java.sql.Date.valueOf(publishedDate) : null);
            } else {
                prepare.setNull(12, java.sql.Types.VARCHAR);
                prepare.setNull(13, java.sql.Types.VARCHAR);
                prepare.setNull(14, java.sql.Types.VARCHAR);
                prepare.setNull(15, java.sql.Types.DATE);
            }

            prepare.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateProduct(
            String productId, String type, String name, String image, String distributor, String description,
            LocalDate addedDate, int stock, float importPrice, float sellPrice, int restrictedAge,
            String isbn, String author, String genre, LocalDate publishedDate) {

        String updateQuery = "UPDATE product SET "
                + "type = ?, name = ?, image = ?, distributor = ?, description = ?, added_date = ?, "
                + "stock = ?, import_price = ?, sell_price = ?, age_restrict = ?, isbn = ?, "
                + "author = ?, genre = ?, pub_date = ? WHERE product_id = ?";

        try (PreparedStatement prepare = connect.prepareStatement(updateQuery)) {
            prepare.setString(1, type);
            prepare.setString(2, name);
            prepare.setString(3, image);
            prepare.setString(4, distributor);
            prepare.setString(5, description);
            prepare.setDate(6, java.sql.Date.valueOf(addedDate));
            prepare.setInt(7, stock);
            prepare.setFloat(8, importPrice);
            prepare.setFloat(9, sellPrice);
            prepare.setInt(10, restrictedAge);

            if ("Book".equals(type)) {
                prepare.setString(11, isbn);
                prepare.setString(12, author);
                prepare.setString(13, genre);
                prepare.setDate(14, publishedDate != null ? java.sql.Date.valueOf(publishedDate) : null);
            } else {
                prepare.setNull(11, java.sql.Types.VARCHAR); // isbn
                prepare.setNull(12, java.sql.Types.VARCHAR); // author
                prepare.setNull(13, java.sql.Types.VARCHAR); // genre
                prepare.setNull(14, java.sql.Types.DATE);    // pub_date
            }

            prepare.setString(15, productId);
            int rowsAffected = prepare.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(String productId) {
        String deleteQuery = "DELETE FROM product WHERE product_id = ?";

        try (PreparedStatement prepare = connect.prepareStatement(deleteQuery)) {
            prepare.setString(1, productId);
            int rowsAffected = prepare.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Book> getAllBooks() {
        String sql = "SELECT * FROM product WHERE type = 'Book'";
        ObservableList<Book> bookList = FXCollections.observableArrayList();

        try (PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                Book book = new Book(
                        result.getString("product_id"),
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getDouble("import_price"),
                        result.getInt("stock"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description"),
                        result.getDate("added_date") != null ? result.getDate("added_date").toLocalDate() : null,
                        result.getInt("age_restrict"),
                        result.getString("isbn"),
                        result.getString("genre"),
                        result.getDate("pub_date") != null ? result.getDate("pub_date").toLocalDate() : null,
                        result.getString("author")
                );
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public ObservableList<Stationery> getAllStationery() {
        String sql = "SELECT * FROM product WHERE type = 'Stationery'";
        ObservableList<Stationery> stationeryList = FXCollections.observableArrayList();

        try (PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                Stationery stationery = new Stationery(
                        result.getString("product_id"),
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getDouble("import_price"),
                        result.getInt("stock"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description"),
                        result.getDate("added_date") != null ? result.getDate("added_date").toLocalDate() : null,
                        result.getInt("age_restrict")
                );
                stationeryList.add(stationery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stationeryList;
    }

    public ObservableList<Toy> getAllToys() {
        String sql = "SELECT * FROM product WHERE type = 'Toy'";
        ObservableList<Toy> toyList = FXCollections.observableArrayList();

        try (PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                Toy toy = new Toy(
                        result.getString("product_id"),
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getDouble("import_price"),
                        result.getInt("stock"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getString("description"),
                        result.getDate("added_date") != null ? result.getDate("added_date").toLocalDate() : null,
                        result.getInt("age_restrict")
                );
                toyList.add(toy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toyList;
    }

    public Product getProductFromProductID(String productId) {
        Product product = null;
        String query = "SELECT * FROM product WHERE product_id = ?";

        try (PreparedStatement prepare = connect.prepareStatement(query)) {
            prepare.setString(1, productId);

            try (ResultSet result = prepare.executeQuery()) {
                while (result.next()) {
                    product = new Product(
                            result.getString("product_id"),
                            result.getString("name"),
                            result.getString("distributor"),
                            result.getDouble("sell_price"),
                            result.getDouble("import_price"),
                            result.getInt("stock"),
                            result.getString("type"),
                            result.getString("image"),
                            result.getString("description"),
                            result.getDate("added_date") != null ? result.getDate("added_date").toLocalDate() : null,
                            result.getInt("age_restrict")
                    );
                }
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateProductStock(List<CartItem> itemList) {
        for(CartItem item : itemList) {
            String sql = "UPDATE product SET " + " stock = stock - ? WHERE product_id = ?";
            try(PreparedStatement statement = connect.prepareStatement(sql)) {
                statement.setInt(1,item.getQuantity());
                statement.setString(2,item.getProduct().getID());

                statement.executeUpdate();

            }catch (SQLException e) {
                e.printStackTrace();
                return false;
                }
        }
        return true;
    }

}

