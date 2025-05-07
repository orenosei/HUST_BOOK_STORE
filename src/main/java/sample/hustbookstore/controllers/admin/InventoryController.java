package sample.hustbookstore.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Product;
import sample.hustbookstore.models.database;
import sample.hustbookstore.utils.CloudinaryUploader;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class InventoryController {

    @FXML
    private AnchorPane inventory_screen;
    @FXML
    private Button add_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button update_btn;

    @FXML
    private Button import_btn;

    @FXML
    private TableColumn<Product, String> col_productID;
    @FXML
    private TableColumn<Product, String> col_productName;
    @FXML
    private TableColumn<Product, Double> col_importPrice;
    @FXML
    private TableColumn<Product, Double> col_sellingPrice;
    @FXML
    private TableColumn<Product, String> col_distributor;
    @FXML
    private TableColumn<Product, String> col_type;
    @FXML
    private TableColumn<Product, LocalDate> col_dateAdded; // hoặc LocalDate nếu bạn dùng kiểu đó
    @FXML
    private TableColumn<Product, Integer> col_stocks;
    @FXML
    private TableColumn<?, ?> col_author;
    @FXML
    private TableColumn<?, ?> col_description;
    @FXML
    private TableColumn<?, ?> col_genre;
    @FXML
    private TableColumn<?, ?> col_imageSource;
    @FXML
    private TableColumn<?, ?> col_isbn;
    @FXML
    private TableColumn<?, ?> col_pubDate;
    @FXML
    private TableColumn<?, ?> col_restrictedAge;



    @FXML
    private TextField inventory_ISBN;

    @FXML
    private TextField inventory_author;

    @FXML
    private TextArea inventory_description;

    @FXML
    private TextField inventory_distributor;

    @FXML
    private DatePicker inventory_publishedDate;
    @FXML
    private CheckComboBox<String> inventory_genre;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private TextField inventory_importPrice;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_productName;

    @FXML
    private TextField inventory_sellingPrice;

    @FXML
    private TextField inventory_stocks;

    @FXML
    private TextField inventory_restrictedAge;

    @FXML
    private Label inventory_genre_label;
    @FXML
    private Label inventory_isbn_label;
    @FXML
    private Label inventory_pubDate_label;
    @FXML
    private Label inventory_description_label;
    @FXML
    private Label inventory_author_label;



    @FXML
    private TableView<Book> inventory_tableView;

    @FXML
    private TextField search_bar;

    @FXML
    private ComboBox<String> inventory_type;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    private Image image;
    private String currentImagePath;

    public void setTypeList(){
        List<String> typeL = new ArrayList<String>();
        typeL.add("Book");
        typeL.add("Toy");
        typeL.add("Stationery");
        ObservableList<String> items = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(items);
    }

    public void setGenreList() {
        List<String> genres = new ArrayList<>();
        genres.add("Adventure");
        genres.add("Alternate History");
        genres.add("Autobiography");
        genres.add("Biography");
        genres.add("Business");
        genres.add("Children's Books");
        genres.add("Classic Literature");
        genres.add("Comedy");
        genres.add("Cooking");
        genres.add("Crime");
        genres.add("Cyberpunk");
        genres.add("Dark Fantasy");
        genres.add("Drama");
        genres.add("Dystopian");
        genres.add("Education");
        genres.add("Epic Fantasy");
        genres.add("Fantasy");
        genres.add("Gothic");
        genres.add("Graphic Novel");
        genres.add("Health & Wellness");
        genres.add("Historical");
        genres.add("Horror");
        genres.add("Light Novel");
        genres.add("LitRPG");
        genres.add("Magical Realism");
        genres.add("Manga");
        genres.add("Manhwa");
        genres.add("Martial Arts");
        genres.add("Memoir");
        genres.add("Mystery");
        genres.add("Mythology");
        genres.add("Philosophical");
        genres.add("Poetry");
        genres.add("Post-Apocalyptic");
        genres.add("Psychological");
        genres.add("Psychology");
        genres.add("Religious");
        genres.add("Romance");
        genres.add("Science");
        genres.add("Science Fiction");
        genres.add("Self-Help");
        genres.add("Slice of Life");
        genres.add("Space Opera");
        genres.add("Steampunk");
        genres.add("Technology");
        genres.add("Thriller");
        genres.add("Time Travel");
        genres.add("Travel");
        genres.add("Urban Fantasy");
        genres.add("War & Military");
        genres.add("Web Novel");
        genres.add("Young Adult");

        // Sắp xếp danh sách theo thứ tự từ điển
//        Collections.sort(genres);

        ObservableList<String> genreList = FXCollections.observableArrayList(genres);
        inventory_genre.getItems().addAll(genreList);
    }

    public ObservableList<Book> dataList() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(new Book(
                        result.getString("id"),
                        result.getString("name"),
                        result.getString("distributor"),
                        result.getDouble("sell_price"),
                        result.getDouble("import_price"),
                        result.getInt("stock"),
                        result.getString("type"),
                        result.getString("image"), // Lấy đường dẫn ảnh
                        result.getString("description"), // Lấy mô tả
                        result.getDate("added_date") != null ? result.getDate("added_date").toLocalDate() : null, // Ngày thêm sản phẩm
                        result.getInt("age_restrict"), // Giới hạn tuổi
                        result.getInt("sell_quantity"), // Số lượng bán
                        result.getString("isbn"), // ISBN
                        result.getString("genre"), // Thể loại
                        result.getDate("pub_date") != null ? result.getDate("pub_date").toLocalDate() : null, // Ngày xuất bản
                        result.getString("author") // Tác giả
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void showData() {
        ObservableList<Book> list = dataList();

        FilteredList<Book> filteredData = new FilteredList<>(list, b -> true);

        // Lắng nghe sự thay đổi trong thanh tìm kiếm
        search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // Nếu thanh tìm kiếm trống, hiển thị tất cả
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Kiểm tra các thuộc tính: name, distributor, author, genre, description
                return (book.getName() != null && book.getName().toLowerCase().contains(lowerCaseFilter))
                        || (book.getDistributor() != null && book.getDistributor().toLowerCase().contains(lowerCaseFilter))
                        || (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerCaseFilter))
                        || (book.getGenre() != null && book.getGenre().toLowerCase().contains(lowerCaseFilter))
                        || (book.getDescription() != null && book.getDescription().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(inventory_tableView.comparatorProperty());

        col_productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_importPrice.setCellValueFactory(new PropertyValueFactory<>("importPrice"));
        col_sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        col_distributor.setCellValueFactory(new PropertyValueFactory<>("distributor"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_dateAdded.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        col_stocks.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_pubDate.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_imageSource.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_restrictedAge.setCellValueFactory(new PropertyValueFactory<>("restrictedAge"));

        inventory_tableView.setItems(sortedData);
    }


    public void handleProductTypeChange() {
        // Kiểm tra xem loại sản phẩm có phải là Book không
        if ("Book".equals(inventory_type.getSelectionModel().getSelectedItem())) {
            inventory_genre_label.setDisable(false);
            inventory_isbn_label.setDisable(false);
            inventory_pubDate_label.setDisable(false);
            inventory_ISBN.setDisable(false);
            inventory_author.setDisable(false);
            inventory_author_label.setDisable(false);
            inventory_publishedDate.setDisable(false);
            inventory_genre.setDisable(false);

        } else {
            inventory_genre_label.setDisable(true);
            inventory_isbn_label.setDisable(true);
            inventory_pubDate_label.setDisable(true);
            inventory_ISBN.setDisable(true);
            inventory_author.setDisable(true);
            inventory_author_label.setDisable(true);
            inventory_publishedDate.setDisable(true);
            inventory_genre.setDisable(true);
        }
    }

    private String currentImageUrl;

    public String getImageUrl(File file) throws Exception {
        CloudinaryUploader uploader = new CloudinaryUploader();
        String imageUrl = uploader.uploadImage(file);
        System.out.println("Uploaded Image URL: " + imageUrl);

        return imageUrl;
    }

    public void setImport_btn(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image Files", "*.png", "*.jpg", "*.webp", "*.jpeg"));
        File file = fileChooser.showOpenDialog(inventory_screen.getScene().getWindow());

        if (file != null) {

//            CloudinaryUploader uploader = new CloudinaryUploader();
//            String imageUrl = uploader.uploadImage(file);
//            System.out.println("Uploaded Image URL: " + imageUrl);


            // Định vị thư mục đích 'img/' trong 'src/main/resources/sample/hustbookstore/img'
//            String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/sample/hustbookstore/img";
//            File targetDirectory = new File(resourcesPath);
//
//            // Kiểm tra nếu thư mục 'img' không tồn tại thì tạo mới
//            if (!targetDirectory.exists()) {
//                targetDirectory.mkdirs();
//            }
//
//            // Lấy tên file và tạo đường dẫn đích
//            String fileName = file.getName();
//            File targetFile = new File(targetDirectory, fileName);


            try {
//                // Sao chép file vào thư mục đích
//                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                // Cập nhật đường dẫn tương đối
//                currentImagePath = "sample/hustbookstore/img/" + fileName;
                //currentImagePath = file.getAbsolutePath();
                currentImageUrl = getImageUrl(file);

                image = new Image(file.toURI().toString(), 1000, 1600, true, true);
                inventory_imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void setAdd_btn() {
        if (
                inventory_productID.getText().isEmpty()
                        || inventory_productName.getText().isEmpty()
                        || inventory_importPrice.getText().isEmpty()
                        || inventory_sellingPrice.getText().isEmpty()
                        || inventory_distributor.getText().isEmpty()
                        || inventory_type.getSelectionModel().getSelectedItem() == null
                        || inventory_restrictedAge.getText().isEmpty()
                        || inventory_stocks.getText().isEmpty()
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_description.getText().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_ISBN.getText().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_author.getText().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_genre.getCheckModel().getCheckedItems().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_publishedDate.getValue() == null)
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly.");
            alert.showAndWait();
        }

        else{
            String checkProductID = "SELECT id FROM product WHERE id = '" + inventory_productID.getText() + "'";
            connect = database.connectDB();

            try{
                statement = connect.createStatement();
                result = statement.executeQuery(checkProductID);

                if(result.next()){ // nếu đã tồn tại sản phẩm thì đưa ra cảnh báo
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_productID.getText() +  " already exists.");
                    alert.showAndWait();
                }
                else{ //nếu chưa tồn tại sản phẩm thì thêm

                    String insertToDatabase;
                    if("Book".equals(inventory_type.getSelectionModel().getSelectedItem())){
                        insertToDatabase = "INSERT INTO product"
                                + "(id, type, name, image, distributor, description, added_date, stock, import_price, sell_price, age_restrict, isbn, author, genre, pub_date) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        prepare = connect.prepareStatement(insertToDatabase);
                        prepare.setString(1, inventory_productID.getText());
                        prepare.setString(2, "Book"); // vì đoạn này đang ở trong điều kiện Book
                        prepare.setString(3, inventory_productName.getText());
                        prepare.setString(4, currentImageUrl);
                        prepare.setString(5, inventory_distributor.getText());
                        prepare.setString(6, inventory_description.getText());
                        prepare.setDate(7, java.sql.Date.valueOf(LocalDate.now())); // dùng ngày hiện tại
                        prepare.setInt(8, Integer.parseInt(inventory_stocks.getText()));
                        prepare.setFloat(9, Float.parseFloat(inventory_importPrice.getText()));
                        prepare.setFloat(10, Float.parseFloat(inventory_sellingPrice.getText()));
                        prepare.setInt(11, Integer.parseInt(inventory_restrictedAge.getText()));
                        prepare.setString(12, inventory_ISBN.getText());
                        prepare.setString(13, inventory_author.getText());
                        prepare.setString(14, String.join(", ", inventory_genre.getCheckModel().getCheckedItems()));
                        prepare.setDate(15, java.sql.Date.valueOf(inventory_publishedDate.getValue()));

                        prepare.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Product added successfully.");
                        alert.showAndWait();
                        showData();

                    }
                    else {
                        insertToDatabase = "INSERT INTO product "
                                + "(id, type, name, image, distributor, description, added_date, stock, import_price, sell_price, age_restrict, isbn, author, genre, pub_date) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        prepare = connect.prepareStatement(insertToDatabase);
                        prepare.setString(1, inventory_productID.getText());
                        prepare.setString(2, inventory_type.getSelectionModel().getSelectedItem());
                        prepare.setString(3, inventory_productName.getText());
                        prepare.setString(4, currentImageUrl);
                        prepare.setString(5, inventory_distributor.getText());
                        prepare.setString(6, inventory_description.getText());
                        prepare.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                        prepare.setInt(8, Integer.parseInt(inventory_stocks.getText()));
                        prepare.setFloat(9, Float.parseFloat(inventory_importPrice.getText()));
                        prepare.setFloat(10, Float.parseFloat(inventory_sellingPrice.getText()));
                        prepare.setInt(11, Integer.parseInt(inventory_restrictedAge.getText()));

                        // Các trường đặc trưng của Book để NULL
                        prepare.setNull(12, java.sql.Types.VARCHAR); // isbn
                        prepare.setNull(13, java.sql.Types.VARCHAR); // author
                        prepare.setNull(14, java.sql.Types.VARCHAR); // genre
                        prepare.setNull(15, java.sql.Types.DATE);    // pub_date

                        prepare.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Product added successfully.");
                        alert.showAndWait();
                        showData();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setClear_btn() {
        inventory_productID.clear();
        inventory_productName.clear();
        inventory_importPrice.clear();
        inventory_sellingPrice.clear();
        inventory_restrictedAge.clear();
        inventory_distributor.clear();
        inventory_ISBN.clear();
        inventory_author.clear();
        inventory_stocks.clear();
        inventory_description.clear();
        inventory_publishedDate.setValue(null);
        inventory_genre.getCheckModel().clearChecks();
        inventory_type.getSelectionModel().clearSelection();
        inventory_imageView.setImage(null);
    }

    public void setUpdate_btn() {
        if (
                inventory_productID.getText().isEmpty()
                        || inventory_productName.getText().isEmpty()
                        || inventory_importPrice.getText().isEmpty()
                        || inventory_sellingPrice.getText().isEmpty()
                        || inventory_distributor.getText().isEmpty()
                        || inventory_type.getSelectionModel().getSelectedItem() == null
                        || inventory_restrictedAge.getText().isEmpty()
                        || inventory_stocks.getText().isEmpty()
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_description.getText().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_ISBN.getText().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_author.getText().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_genre.getCheckModel().getCheckedItems().isEmpty())
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && inventory_publishedDate.getValue() == null)
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly.");
            alert.showAndWait();
        } else {
            String updateQuery;
            connect = database.connectDB();

            try {
                if ("Book".equals(inventory_type.getSelectionModel().getSelectedItem())) {
                    updateQuery = "UPDATE product SET "
                            + "type = ?, name = ?, image = ?, distributor = ?, description = ?, added_date = ?, "
                            + "stock = ?, import_price = ?, sell_price = ?, age_restrict = ?, isbn = ?, "
                            + "author = ?, genre = ?, pub_date = ? WHERE id = ?";

                    prepare = connect.prepareStatement(updateQuery);
                    prepare.setString(1, "Book");
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, currentImageUrl);
                    prepare.setString(4, inventory_distributor.getText());
                    prepare.setString(5, inventory_description.getText());
                    prepare.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                    prepare.setInt(7, Integer.parseInt(inventory_stocks.getText()));
                    prepare.setFloat(8, Float.parseFloat(inventory_importPrice.getText()));
                    prepare.setFloat(9, Float.parseFloat(inventory_sellingPrice.getText()));
                    prepare.setInt(10, Integer.parseInt(inventory_restrictedAge.getText()));
                    prepare.setString(11, inventory_ISBN.getText());
                    prepare.setString(12, inventory_author.getText());
                    prepare.setString(13, String.join(", ", inventory_genre.getCheckModel().getCheckedItems()));
                    prepare.setDate(14, java.sql.Date.valueOf(inventory_publishedDate.getValue()));
                    prepare.setString(15, inventory_productID.getText());
                } else {
                    updateQuery = "UPDATE product SET "
                            + "type = ?, name = ?, image = ?, distributor = ?, description = ?, added_date = ?, "
                            + "stock = ?, import_price = ?, sell_price = ?, age_restrict = ?, isbn = ?, "
                            + "author = ?, genre = ?, pub_date = ? WHERE id = ?";

                    prepare = connect.prepareStatement(updateQuery);
                    prepare.setString(1, inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, currentImageUrl);
                    prepare.setString(4, inventory_distributor.getText());
                    prepare.setString(5, inventory_description.getText());
                    prepare.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                    prepare.setInt(7, Integer.parseInt(inventory_stocks.getText()));
                    prepare.setFloat(8, Float.parseFloat(inventory_importPrice.getText()));
                    prepare.setFloat(9, Float.parseFloat(inventory_sellingPrice.getText()));
                    prepare.setInt(10, Integer.parseInt(inventory_restrictedAge.getText()));

                    // Đặt NULL cho các trường không áp dụng với loại sản phẩm không phải Book
                    prepare.setNull(11, java.sql.Types.VARCHAR); // isbn
                    prepare.setNull(12, java.sql.Types.VARCHAR); // author
                    prepare.setNull(13, java.sql.Types.VARCHAR); // genre
                    prepare.setNull(14, java.sql.Types.DATE);    // pub_date
                    prepare.setString(15, inventory_productID.getText());
                }

                int rowsAffected = prepare.executeUpdate();

                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product updated successfully.");
                    alert.showAndWait();
                    showData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product update failed. Please try again.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setDelete_btn() {
        if (inventory_productID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        } else {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation Message");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                String deleteQuery = "DELETE FROM product WHERE id = ?";
                connect = database.connectDB();

                try {
                    prepare = connect.prepareStatement(deleteQuery);
                    prepare.setString(1, inventory_productID.getText());

                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Product deleted successfully.");
                        alert.showAndWait();
                        showData();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to delete the product. Please try again.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void inventorySelectData() {
        Book prod = inventory_tableView.getSelectionModel().getSelectedItem();
        int index = inventory_tableView.getSelectionModel().getSelectedIndex();

        if (index < 0 || prod == null) return;

        inventory_productID.setText(prod.getID());
        inventory_productName.setText(prod.getName());
        inventory_distributor.setText(prod.getDistributor());
        inventory_stocks.setText(String.valueOf(prod.getStock()));
        inventory_importPrice.setText(String.valueOf(prod.getImportPrice()));
        inventory_sellingPrice.setText(String.valueOf(prod.getSellPrice()));
        inventory_restrictedAge.setText(String.valueOf(prod.getRestrictedAge()));
        inventory_type.getSelectionModel().select(prod.getType());
        inventory_description.setText(prod.getDescription());

//        try {
//            // Kiểm tra đường dẫn ảnh có hợp lệ không
//            if (prod.getImage() != null && !prod.getImage().isEmpty()) {
//                // Chuyển đổi đường dẫn tương đối thành URL
//                String relativePath = prod.getImage(); // sample/hustbookstore/img/pocari.png
//                String imagePath = getClass().getResource("/" + relativePath).toExternalForm();
//
//                // Tạo đối tượng Image từ URL
//                Image img = new Image(imagePath, 1000, 1600, true, true);
//                inventory_imageView.setImage(img);
//                currentImagePath = prod.getImage();
//            } else {
//                // Xóa ảnh nếu đường dẫn rỗng hoặc null
//                inventory_imageView.setImage(null);
//                currentImagePath = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            inventory_imageView.setImage(null); // Xóa ảnh trong trường hợp lỗi
//            currentImagePath = null;
//        }

        try {
            if (prod.getImage() != null && !prod.getImage().isEmpty()) {
                // Sử dụng URL từ cloudinary
                String imageUrl = prod.getImage();
                Image img = new Image(imageUrl, 1000, 1600, true, true);
                inventory_imageView.setImage(img);
                currentImageUrl = imageUrl;
            } else {
                inventory_imageView.setImage(null);
                currentImageUrl = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            inventory_imageView.setImage(null);
            currentImageUrl = null;
        }


        if ("Book".equals(prod.getType())) {
            inventory_author.setText(prod.getAuthor() != null ? prod.getAuthor() : "");
            inventory_publishedDate.setValue(prod.getPublishedDate());
            inventory_ISBN.setText(prod.getIsbn() != null ? prod.getIsbn() : "");
            inventory_genre.getCheckModel().clearChecks();
            if (prod.getGenre() != null) {
                String[] genres = prod.getGenre().split(",");
                for (String g : genres) {
                    inventory_genre.getCheckModel().check(g.trim());
                }
            }
        } else {
            // Xóa dữ liệu liên quan đến sách nếu không phải loại sách
            inventory_author.clear();
            inventory_publishedDate.setValue(null);
            inventory_ISBN.clear();
            inventory_genre.getCheckModel().clearChecks();
        }
    }
    
    public void initialize() {    // buộc phải có, giải thích trong buổi họp nhóm tiếp theo
        setTypeList();
        setGenreList();
        handleProductTypeChange();
        showData();
    }

}
