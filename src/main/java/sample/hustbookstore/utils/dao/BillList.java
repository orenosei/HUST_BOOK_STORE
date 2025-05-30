package sample.hustbookstore.utils.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import sample.hustbookstore.models.Bill;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.CartItem;
import sample.hustbookstore.models.Product;
import sample.hustbookstore.utils.recommendSystem.BookIndexer;
import sample.hustbookstore.utils.recommendSystem.BookRecommender;
import sample.hustbookstore.utils.cloud.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static sample.hustbookstore.LaunchApplication.localInventory;
import static sample.hustbookstore.LaunchApplication.localUser;

public class BillList {
    private static Connection connect;

    public Bill prepareBill(int userId, List<CartItem> cartItems, float discount) {

        double totalPrice = 0;
        double totalProfit = 0;

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product != null) {
                double sellPrice = product.getSellPrice();
                double importPrice = product.getImportPrice();
                int quantity = item.getQuantity();

                totalPrice += sellPrice * quantity;
                totalProfit += (sellPrice - importPrice) * quantity;
            }
        }

        return new Bill(
                userId,
                totalPrice,
                totalProfit * (100 - discount) / 100,
                LocalDate.now()
        );
    }

    public boolean addBill(Bill bill, List<CartItem> selectedItems) {
        String sqlBill = "INSERT INTO bill (user_id, total_price, profit, purchase_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement billStatement = connect.prepareStatement(sqlBill, PreparedStatement.RETURN_GENERATED_KEYS)) {

            billStatement.setInt(1, bill.getUserID());
            billStatement.setDouble(2, bill.getTotalPrice());
            billStatement.setDouble(3, bill.getProfit());
            billStatement.setDate(4, Date.valueOf(bill.getPurchasedDate()));

            int affectedRows = billStatement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = billStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    return false;
                }
                int billId = generatedKeys.getInt(1);

                String sqlItem = "INSERT INTO bill_item (bill_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
                for (CartItem item : selectedItems) {
                    try (PreparedStatement itemStatement = connect.prepareStatement(sqlItem)) {
                        itemStatement.setInt(1, billId);
                        itemStatement.setString(2, item.getProductId());
                        itemStatement.setInt(3, item.getQuantity());

                        Product product = item.getProduct();
                        if (product == null) {
                            return false;
                        }
                        itemStatement.setDouble(4, product.getSellPrice());

                        itemStatement.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int countBill() {
        String sql = "SELECT COUNT(bill_id) FROM bill";
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

    public static float calculateIncome(Date date1, Date date2) {
        String sql = "SELECT SUM(profit) FROM bill WHERE purchase_date BETWEEN ? AND ?";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setDate(1, date1);
            stmt.setDate(2, date2);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<XYChart.Data<String, Float>> getIncomeDataByDate(Date day1, Date day2) {
        List<XYChart.Data<String, Float>> dataList = new ArrayList<>();
        String sql = "SELECT purchase_date, SUM(profit) FROM bill WHERE purchase_date BETWEEN ? AND ? GROUP BY purchase_date ORDER BY TIMESTAMP(purchase_date)";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setDate(1, day1);
            stmt.setDate(2, day2);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dataList.add(new XYChart.Data<>(rs.getString(1), rs.getFloat(2)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<XYChart.Data<String, Integer>> getOrderDataByDate(Date day1, Date day2) {
        List<XYChart.Data<String, Integer>> dataList = new ArrayList<>();
        String sql = "SELECT purchase_date, COUNT(bill_id) FROM bill WHERE purchase_date BETWEEN ? AND ? GROUP BY purchase_date ORDER BY TIMESTAMP(purchase_date)";
        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setDate(1, day1);
            stmt.setDate(2, day2);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dataList.add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<Map<String, Object>> getBillItemsWithProductName(int billId) {
        List<Map<String, Object>> items = new ArrayList<>();
        String sql = "SELECT p.name, bi.quantity, bi.price_at_purchase "
                + "FROM bill_item bi "
                + "JOIN product p ON bi.product_id = p.product_id "
                + "WHERE bi.bill_id = ?";

        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, billId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("product_name", rs.getString("name"));
                    item.put("quantity", rs.getInt("quantity"));
                    item.put("price", rs.getDouble("price_at_purchase"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<Map<String, Object>> getAllBillsWithUserName() {
        List<Map<String, Object>> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, u.name, b.total_price, b.profit, b.purchase_date "
                + "FROM bill b JOIN user u ON b.user_id = u.user_id";

        try (PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> bill = new HashMap<>();
                bill.put("bill_id", rs.getInt("bill_id"));
                bill.put("name", rs.getString("name"));
                bill.put("total_price", rs.getDouble("total_price"));
                bill.put("profit", rs.getDouble("profit"));
                bill.put("purchase_date", rs.getDate("purchase_date").toLocalDate());
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public ObservableList<Book> getTrendingBooks() {
        ObservableList<Book> trendingBooks = FXCollections.observableArrayList();
        String query = """
        SELECT 
            p.product_id,
            p.name,
            p.distributor,
            p.sell_price,
            p.import_price,
            p.stock,
            p.type,
            p.image,
            p.description,
            p.added_date,
            p.age_restrict,
            p.isbn,
            p.genre,
            p.author,
            p.pub_date,
            SUM(bi.quantity) AS total_sold
        FROM bill_item bi
        JOIN bill b ON bi.bill_id = b.bill_id
        JOIN product p ON bi.product_id = p.product_id
        WHERE p.type = 'Book'
            AND b.purchase_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
        GROUP BY p.product_id
        ORDER BY total_sold DESC
        LIMIT 5
    """;

        try (PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("distributor"),
                        resultSet.getDouble("sell_price"),
                        resultSet.getDouble("import_price"),
                        resultSet.getInt("stock"),
                        resultSet.getString("type"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getDate("added_date").toLocalDate(),
                        resultSet.getInt("age_restrict"),
                        resultSet.getString("isbn"),
                        resultSet.getString("genre"),
                        resultSet.getDate("pub_date").toLocalDate(),
                        resultSet.getString("author")
                );

                trendingBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error loading trending books: " + e.getMessage());
            e.printStackTrace();
        }

        return trendingBooks;
    }

    public ObservableList<Book> getRecommendBooks() throws Exception {
        StringBuilder sb = new StringBuilder();

        ObservableList<Book> AllBook = localInventory.getAllBooks();
        BookIndexer indexer = new BookIndexer();
        indexer.indexBooks(AllBook);

        String sql = "SELECT p.product_id, p.isbn, p.image, p.name, p.author, p.genre, p.description, p.sell_price "
                + "FROM bill_item bi JOIN bill b ON bi.bill_id = b.bill_id JOIN product p ON bi.product_id = p.product_id "
                + "WHERE p.type = 'Book' AND b.user_id = ? ORDER BY b.purchase_date DESC LIMIT 5";

        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, localUser.getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getString("product_id"),
                            rs.getString("isbn"),
                            rs.getString("image"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getString("description"),
                            rs.getDouble("sell_price")
                    );
                    sb.append(book.getcombinedText());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BookRecommender recommender = new BookRecommender();
        ObservableList<Book> recommendbooks = recommender.searchSimilarBooks(sb.toString(),5);

        return recommendbooks;
    }

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

}
