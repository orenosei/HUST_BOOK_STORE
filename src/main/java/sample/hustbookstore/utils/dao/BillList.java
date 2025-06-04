package sample.hustbookstore.utils.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import sample.hustbookstore.models.*;
import sample.hustbookstore.utils.recommendSystem.BookIndexer;
import sample.hustbookstore.utils.recommendSystem.BookRecommender;
import sample.hustbookstore.utils.cloud.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localUser;

public class BillList {
    private static Connection connect;

    public static Bill prepareBill(User user, List<CartItem> cartItems, float discount) {
        double totalPrice = 0;
        double totalProfit = 0;
        List<BillItem> billItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product != null) {
                double sellPrice = product.getSellPrice();
                double importPrice = product.getImportPrice();
                int quantity = item.getQuantity();

                totalPrice += sellPrice * quantity;
                totalProfit += (sellPrice - importPrice) * quantity;

                billItems.add(new BillItem(
                        product,
                        quantity,
                        sellPrice
                ));
            }
        }

        return new Bill(
                -1,
                totalPrice,
                totalProfit * (100 - discount) / 100,
                LocalDate.now(),
                billItems,
                user
        );
    }

    public static boolean addBill(Bill bill, List<CartItem> selectedItems) {
        String sqlBill = "INSERT INTO bill (user_id, total_price, profit, purchase_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement billStatement = connect.prepareStatement(sqlBill, PreparedStatement.RETURN_GENERATED_KEYS)) {

            billStatement.setInt(1, bill.getUser().getUserId());
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
                        itemStatement.setString(2, item.getProduct().getID());
                        itemStatement.setInt(3, item.getQuantity());
                        itemStatement.setDouble(4, item.getProduct().getSellPrice());
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

    public static List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.user_id, b.total_price, b.profit, b.purchase_date, u.name "
                + "FROM bill b JOIN user u ON b.user_id = u.user_id";

        try (PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int billId = rs.getInt("bill_id");

                List<BillItem> items = new ArrayList<>();
                try (PreparedStatement itemsStmt = connect.prepareStatement(
                        "SELECT product_id, quantity, price_at_purchase " +
                                "FROM bill_item WHERE bill_id = ?")) {
                    itemsStmt.setInt(1, billId);
                    ResultSet itemsRs = itemsStmt.executeQuery();
                    while (itemsRs.next()) {
                        items.add(new BillItem(
                                Inventory.getProductFromProductID(itemsRs.getString("product_id")),
                                itemsRs.getInt("quantity"),
                                itemsRs.getDouble("price_at_purchase")
                        ));
                    }
                }

                bills.add(new Bill(
                        billId,
                        rs.getDouble("total_price"),
                        rs.getDouble("profit"),
                        rs.getDate("purchase_date").toLocalDate(),
                        items,
                        UserList.getUserFromId(rs.getInt("user_id"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public static List<Bill> getUserBills(int userId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.user_id, b.total_price, b.profit, b.purchase_date, u.name "
                + "FROM bill b JOIN user u ON b.user_id = u.user_id "
                + "WHERE b.user_id = ?";

        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int billId = rs.getInt("bill_id");

                    List<BillItem> items = new ArrayList<>();
                    String itemsSql = "SELECT product_id, quantity, price_at_purchase FROM bill_item WHERE bill_id = ?";
                    try (PreparedStatement itemsStmt = connect.prepareStatement(itemsSql)) {
                        itemsStmt.setInt(1, billId);
                        try (ResultSet itemsRs = itemsStmt.executeQuery()) {
                            while (itemsRs.next()) {
                                items.add(new BillItem(
                                        Inventory.getProductFromProductID(itemsRs.getString("product_id")),
                                        itemsRs.getInt("quantity"),
                                        itemsRs.getDouble("price_at_purchase")
                                ));
                            }
                        }
                    }

                    bills.add(new Bill(
                            billId,
                            rs.getDouble("total_price"),
                            rs.getDouble("profit"),
                            rs.getDate("purchase_date").toLocalDate(),
                            items,
                            UserList.getUserFromId(rs.getInt("user_id"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }



    public static ObservableList<Book> getTrendingBooks() {
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

    public static ObservableList<Book> getRecommendBooks() throws Exception {
        StringBuilder sb = new StringBuilder();

        ObservableList<Book> AllBook = Inventory.getAllBooks();
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

        return recommender.searchSimilarBooks(sb.toString(),5);
    }

    public static void initialize() {
        connect = database.connectDB();
        if (connect == null) {
            throw new IllegalStateException("Unable to connect to the database.");
        }
    }

}
