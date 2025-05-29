package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.hustbookstore.models.Book;

import java.net.MalformedURLException;
import java.net.URL;

public class UserRecommendCardController {


    @FXML
    private TextField authorField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField genreField;

    @FXML
    private ImageView imageField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextArea nameField;

    @FXML
    private Text priceField;


    public void setData(Book book) {
        authorField.setText(book.getAuthor());
        descriptionField.setText(book.getDescription());
        genreField.setText(book.getGenre());
        isbnField.setText(book.getIsbn());
        nameField.setText(book.getName());
        priceField.setText(book.getSellPrice().toString());

        String imagePath = book.getImage();
        Image image = null;
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                URL imageUrl = new URL(imagePath);
                image = new Image(imageUrl.toExternalForm(), 1000, 1600, true, true);
            } else {
                image = new Image(getClass().getResource("/sample/hustbookstore/img/notfound.jpg").toExternalForm(), 1000, 1600, true, true);
            }
        } catch (MalformedURLException e) {
            URL resourceUrl = getClass().getResource("/" + imagePath);
            if (resourceUrl != null) {
                image = new Image(resourceUrl.toExternalForm(), 1000, 1600, true, true);
            } else {
                image = new Image(getClass().getResource("/sample/hustbookstore/img/notfound.jpg").toExternalForm(), 1000, 1600, true, true);
            }
        }
        imageField.setImage(image);
    }
}
