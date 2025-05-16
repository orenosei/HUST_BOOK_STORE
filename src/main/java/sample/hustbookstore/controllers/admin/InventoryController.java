package sample.hustbookstore.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
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

import static sample.hustbookstore.LaunchApplication.localInventory;


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
    private TableColumn<Product, LocalDate> col_dateAdded;
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


    public void showData() {
        ObservableList<Book> list = localInventory.getAllProducts();

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
            try {
                currentImageUrl = getImageUrl(file);

                image = new Image(file.toURI().toString(), 100, 160, true, true);
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
            return;
        }

        if (localInventory.isProductExists(inventory_productID.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(inventory_productID.getText() + " already exists.");
            alert.showAndWait();
        } else {
            String type = inventory_type.getSelectionModel().getSelectedItem();

            boolean success = localInventory.addProduct(
                    inventory_productID.getText(),
                    inventory_productName.getText(),
                    inventory_distributor.getText(),
                    Double.parseDouble(inventory_sellingPrice.getText()),
                    Double.parseDouble(inventory_importPrice.getText()),
                    Integer.parseInt(inventory_stocks.getText()),
                    type,
                    currentImageUrl,
                    inventory_description.getText(),
                    LocalDate.now(),
                    Integer.parseInt(inventory_restrictedAge.getText()),
                    "Book".equals(type) ? inventory_ISBN.getText() : null,
                    "Book".equals(type) ? String.join(", ", inventory_genre.getCheckModel().getCheckedItems()) : null,
                    "Book".equals(type) ? inventory_publishedDate.getValue() : null,
                    "Book".equals(type) ? inventory_author.getText() : null
            );

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Product added successfully.");
                alert.showAndWait();

                if (homeScreenController != null) {
                    homeScreenController.reloadStore();
                }

                showData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add product.");
                alert.showAndWait();
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
                        || ("Book".equals(inventory_type.getSelectionModel().getSelectedItem()) && (
                        inventory_description.getText().isEmpty()
                                || inventory_ISBN.getText().isEmpty()
                                || inventory_author.getText().isEmpty()
                                || inventory_genre.getCheckModel().getCheckedItems().isEmpty()
                                || inventory_publishedDate.getValue() == null
                ))
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all the fields correctly.");
            alert.showAndWait();
            return;
        }

        String type = inventory_type.getSelectionModel().getSelectedItem();
        boolean isUpdated = localInventory.updateProduct(
                inventory_productID.getText(),
                type,
                inventory_productName.getText(),
                currentImageUrl,
                inventory_distributor.getText(),
                inventory_description.getText(),
                LocalDate.now(),
                Integer.parseInt(inventory_stocks.getText()),
                Float.parseFloat(inventory_importPrice.getText()),
                Float.parseFloat(inventory_sellingPrice.getText()),
                Integer.parseInt(inventory_restrictedAge.getText()),
                "Book".equals(type) ? inventory_ISBN.getText() : null,
                "Book".equals(type) ? inventory_author.getText() : null,
                "Book".equals(type) ? String.join(", ", inventory_genre.getCheckModel().getCheckedItems()) : null,
                "Book".equals(type) ? inventory_publishedDate.getValue() : null
        );

        if (isUpdated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Product updated successfully.");
            alert.showAndWait();

            if (homeScreenController != null) {
                homeScreenController.reloadStore();
            }
            showData();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Product update failed. Please try again.");
            alert.showAndWait();
        }
    }

    public void setDelete_btn() {
        if (inventory_productID.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation Message");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = localInventory.deleteProduct(inventory_productID.getText());

            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Product deleted successfully.");
                alert.showAndWait();
                setClear_btn();

                if (homeScreenController != null) {
                    homeScreenController.reloadStore();
                }
                showData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete the product. Please try again.");
                alert.showAndWait();
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

        loadImageAsync(prod.getImage());

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
            inventory_author.clear();
            inventory_publishedDate.setValue(null);
            inventory_ISBN.clear();
            inventory_genre.getCheckModel().clearChecks();
        }
    }

    private Task<Image> imageLoadingTask;
    private void loadImageAsync(String imageUrl) {
        if (imageLoadingTask != null && imageLoadingTask.isRunning()) {
            imageLoadingTask.cancel();
        }

        inventory_imageView.setImage(null);
        currentImageUrl = null;

        if (imageUrl == null || imageUrl.isEmpty()) return;

        imageLoadingTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                return new Image(imageUrl, 100, 160, true, true);
            }
        };
        imageLoadingTask.setOnSucceeded(e -> {
            if (!imageLoadingTask.isCancelled()) {
                Image img = imageLoadingTask.getValue();
                inventory_imageView.setImage(img);
                currentImageUrl = imageUrl;
            }
        });
        imageLoadingTask.setOnFailed(e -> {
            inventory_imageView.setImage(null);
            currentImageUrl = null;
        });
        Thread thread = new Thread(imageLoadingTask);
        thread.setDaemon(true);
        thread.start();
    }

    private HomeScreenController homeScreenController;

    public void setHomeScreenController(HomeScreenController homeScreenController) {
        this.homeScreenController = homeScreenController;
    }


    public void initialize() {    // buộc phải có, giải thích trong buổi họp nhóm tiếp theo
        setTypeList();
        setGenreList();
        handleProductTypeChange();
        showData();
    }

}
