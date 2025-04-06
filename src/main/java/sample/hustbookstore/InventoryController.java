package sample.hustbookstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.controlsfx.control.CheckComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {

    @FXML
    private Button add_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private TableColumn<?, ?> col_dateAdded;

    @FXML
    private TableColumn<?, ?> col_distributor;

    @FXML
    private TableColumn<?, ?> col_importPrice;

    @FXML
    private TableColumn<?, ?> col_productID;

    @FXML
    private TableColumn<?, ?> col_productName;

    @FXML
    private TableColumn<?, ?> col_sellingPrice;

    @FXML
    private TableColumn<?, ?> col_type;

    @FXML
    private TableColumn<?, ?> col_stocks;

    @FXML
    private Button delete_btn;

    @FXML
    private Button import_btn;

    @FXML
    private TextField inventory_ISBN;

    @FXML
    private TextField inventory_author;

    @FXML
    private TextField inventory_distributor;


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
    private DatePicker inventory_publishedDate;

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

    @FXML
    private Button update_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


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
                        result.getString("productID"),
                        result.getString("productName"),
                        result.getDouble("importPrice"),
                        result.getDouble("sellingPrice"),
                        result.getString("distributor"),
                        result.getString("type"),
                        result.getString("dateAdded")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public void showData() {
        ObservableList<Product> list = dataList();

        col_productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_importPrice.setCellValueFactory(new PropertyValueFactory<>("importPrice"));
        col_sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        col_distributor.setCellValueFactory(new PropertyValueFactory<>("distributor"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_dateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));

        inventory_tableView.setItems(list);
    }




    public void initialize() {    // buộc phải có, giải thích trong buổi họp nhóm tiếp theo
        setTypeList();
        setGenreList();
        //showData();
    }

}
