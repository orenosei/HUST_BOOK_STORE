package sample.hustbookstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private TableColumn<Product, java.sql.Date> col_dateAdded; // hoặc LocalDate nếu bạn dùng kiểu đó

    @FXML
    private TableColumn<Product, Integer> col_stocks;



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
    private TableView<Product> inventory_tableView;

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

    public ObservableList<Product> dataList(){
        ObservableList<Product> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                list.add(new Product(
                        result.getString("ID"),
                        result.getString("name"),
                        result.getDouble("import_price"),
                        result.getDouble("sell_price"),
                        result.getString("distributor"),
                        result.getString("type"),
                        result.getDate("added_date"),
                        result.getInt("stock")
                ));

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public void showData() {
        ObservableList<Product> list = dataList();

        col_productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_importPrice.setCellValueFactory(new PropertyValueFactory<>("importPrice"));
        col_sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        col_distributor.setCellValueFactory(new PropertyValueFactory<>("distributor"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_dateAdded.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        col_stocks.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_tableView.setItems(list);
    }


    public void setImport_btn() {
        FileChooser fileChooser = new FileChooser();

        // Đặt filter cho chỉ file ảnh
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image Files", "*.png", "*.jpg", "*.webp", "*.jpeg"));

        // Mở cửa sổ chọn file
        File file = fileChooser.showOpenDialog(inventory_screen.getScene().getWindow());

        if (file != null) {
            // Định vị thư mục đích 'img/' trong 'src/main/resources/sample/hustbookstore/img'
            String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/sample/hustbookstore/img";
            File targetDirectory = new File(resourcesPath);

            // Kiểm tra nếu thư mục 'img' không tồn tại thì tạo mới
            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs();
            }

            // Lấy tên file và tạo đường dẫn đích
            String fileName = file.getName();
            File targetFile = new File(targetDirectory, fileName);

            try {
                // Sao chép file vào thư mục đích
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn tương đối
                currentImagePath = "sample/hustbookstore/img/" + fileName;

                // Hiển thị ảnh trên ImageView
                image = new Image(targetFile.toURI().toString(), 1000, 1600, true, true);
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
                        prepare.setString(4, currentImagePath);
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
                        prepare.setString(2, inventory_type.getSelectionModel().getSelectedItem()); // VD: "Toy", "Stationery"
                        prepare.setString(3, inventory_productName.getText());
                        prepare.setString(4, currentImagePath);
                        prepare.setString(5, inventory_distributor.getText());
                        prepare.setString(6, inventory_description.getText()); // Mô tả không để trống
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



    public void initialize() {    // buộc phải có, giải thích trong buổi họp nhóm tiếp theo
        setTypeList();
        setGenreList();
        showData();
    }

}
