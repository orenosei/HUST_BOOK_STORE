package sample.hustbookstore.controllers.base;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.utils.CloudinaryService;

public abstract class UserBaseFeatureCardController {

    @FXML
    protected TextField authorField;

    @FXML
    protected TextArea descriptionField;

    @FXML
    protected TextField genreField;

    @FXML
    protected ImageView imageField;

    @FXML
    protected TextField isbnField;

    @FXML
    protected TextArea nameField;

    @FXML
    protected Text priceField;

    public void setData(Book book) {
        authorField.setText(book.getAuthor());
        descriptionField.setText(book.getDescription());
        genreField.setText(book.getGenre());
        isbnField.setText(book.getIsbn());
        nameField.setText(book.getName());
        priceField.setText(book.getSellPrice().toString());

        String imagePath = book.getImage();
        Image image = CloudinaryService.loadImage(imagePath, 1000, 1600);
        imageField.setImage(image);
    }
}