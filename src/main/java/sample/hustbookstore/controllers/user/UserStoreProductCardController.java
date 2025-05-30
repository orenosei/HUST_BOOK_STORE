package sample.hustbookstore.controllers.user;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Product;
import sample.hustbookstore.models.Stationery;
import sample.hustbookstore.models.Toy;
import sample.hustbookstore.utils.cloud.CloudinaryService;
import sample.hustbookstore.controllers.base.ProductCardSetData;

import static sample.hustbookstore.LaunchApplication.localCart;
import static sample.hustbookstore.controllers.user.UserStoreController.arya;

public class UserStoreProductCardController implements ProductCardSetData {

    @FXML
    private AnchorPane productCard;

    @FXML
    private ImageView productImage;

    @FXML
    private TextArea productName;

    @FXML
    private TextField productSellPrice;

    @FXML
    private Button addToCart_btn;

    @FXML
    private Spinner<Integer> productSpinner;

    @FXML
    private Label bookAuthor;

    @FXML
    private TextArea bookDescription;

    @FXML
    private TextArea bookGenre;

    @FXML
    private TextArea otherDescription;

    @FXML
    private Label otherDistributor;

    @FXML
    private Button bookUp_btn;

    @FXML
    private Button otherUp_btn;

    @FXML
    private Button down_btn;

    @FXML
    private AnchorPane bookInfoPopup;

    @FXML
    private AnchorPane otherInfoPopup;

    @FXML
    private Button askAIButton;

    private Book bookData;
    private Stationery stationeryData;
    private Toy toyData;

    private Image image;

    private SpinnerValueFactory<Integer> spin;

    private int currenttype;

    private Product productAddToCart;

    public void setData(Product prodData) {
        this.productAddToCart = prodData;
        String imagePath;
        if (prodData instanceof Book) {
            Book prod_to_book = (Book) prodData;
            this.bookData = prod_to_book;

            productName.setText(bookData.getName());
            bookAuthor.setText(bookData.getAuthor());
            bookDescription.setText(bookData.getDescription());
            bookGenre.setText(bookData.getGenre());
            productSellPrice.setText(String.valueOf(bookData.getSellPrice()));

            setQuantity(bookData.getStock());
            imagePath = bookData.getImage();

            currenttype = 1;
        } else if (prodData instanceof Stationery){
            askAIButton.setVisible(false);

            Stationery prod_to_stationery = (Stationery) prodData;
            this.stationeryData = prod_to_stationery;

            productName.setText(stationeryData.getName());
            otherDistributor.setText(stationeryData.getDistributor());
            otherDescription.setText(stationeryData.getDescription());
            productSellPrice.setText(String.valueOf(stationeryData.getSellPrice()));

            setQuantity(stationeryData.getStock());
            imagePath = stationeryData.getImage();

            currenttype = 2;
        } else {
            askAIButton.setVisible(false);

            Toy prod_to_toy = (Toy) prodData;
            this.toyData = prod_to_toy;

            productName.setText(toyData.getName());
            otherDistributor.setText(toyData.getDistributor());
            otherDescription.setText(toyData.getDescription());
            productSellPrice.setText(String.valueOf(toyData.getSellPrice()));

            setQuantity(toyData.getStock());
            imagePath = toyData.getImage();

            currenttype = 3;
        }

        Image image = CloudinaryService.loadImage(imagePath);
        productImage.setImage(image);
    }

    public void setQuantity(int qty) {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, qty, 0);
        productSpinner.setValueFactory(spin);
    }

    protected void showBookInfoAnimation() {
        Rectangle clip = new Rectangle();
        clip.setWidth(bookInfoPopup.getPrefWidth());
        clip.setHeight(0);
        bookInfoPopup.setClip(clip);

        bookInfoPopup.setVisible(true);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.5),
                new KeyValue(clip.heightProperty(), bookInfoPopup.getPrefHeight())
        ));
        timeline.play();
    }

    protected void showOtherInfoAnimation() {
        Rectangle clip = new Rectangle();
        clip.setWidth(otherInfoPopup.getPrefWidth());
        clip.setHeight(0);
        otherInfoPopup.setClip(clip);

        otherInfoPopup.setVisible(true);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.5),
                new KeyValue(clip.heightProperty(), otherInfoPopup.getPrefHeight())
        ));
        timeline.play();
    }

    protected void hideBookInfoAnimation() {
        Node clip = bookInfoPopup.getClip();

        if (clip == null || !(clip instanceof Rectangle)) {
            Rectangle newClip = new Rectangle();
            newClip.setWidth(bookInfoPopup.getPrefWidth());
            newClip.setHeight(bookInfoPopup.getPrefHeight());
            bookInfoPopup.setClip(newClip);
            clip = newClip;
        }

        Rectangle rectClip = (Rectangle) clip;

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.5),
                new KeyValue(rectClip.heightProperty(), 0)
        ));

        timeline.setOnFinished(event -> bookInfoPopup.setVisible(false));
        timeline.play();
    }

    protected void hideOtherInfoAnimation() {
        Node clip = otherInfoPopup.getClip();

        if (clip == null || !(clip instanceof Rectangle)) {
            Rectangle newClip = new Rectangle();
            newClip.setWidth(otherInfoPopup.getPrefWidth());
            newClip.setHeight(otherInfoPopup.getPrefHeight());
            otherInfoPopup.setClip(newClip);
            clip = newClip;
        }

        Rectangle rectClip = (Rectangle) clip;

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0.5),
                new KeyValue(rectClip.heightProperty(), 0)
        ));

        timeline.setOnFinished(event -> otherInfoPopup.setVisible(false));
        timeline.play();
    }

    @FXML
    private void handleCardButtonAction(ActionEvent event) {
        if (event.getSource() == down_btn) {
            if (currenttype == 1) {
                showBookInfoAnimation();
            } else {
                showOtherInfoAnimation();
            }
        }
    }

    @FXML
    private void handleBookMoreInfoButtonAction(ActionEvent event) {
        if (event.getSource() == bookUp_btn) {
            hideBookInfoAnimation();
        }
    }

    @FXML
    private void handleOtherMoreInfoButtonAction(ActionEvent event) {
        if (event.getSource() == otherUp_btn) {
            hideOtherInfoAnimation();
        }
    }

    @FXML
    private void handleAskAIButtonAction(ActionEvent event) {
        arya.askAi((Book) productAddToCart);
    }


    @FXML
    public void handleAddToCartButton(ActionEvent event) {
        if (event.getSource() == addToCart_btn) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Processing...");
            alert.show();

            Task<Boolean> task = new Task<>() {
                @Override
                protected Boolean call() {
                    return localCart.addProductToCart(productAddToCart.getID(), productSpinner.getValue());
                }
            };

            task.setOnSucceeded(e -> {
                boolean success = task.getValue();

                Platform.runLater(() -> {
                    if (success) {
                        alert.close();
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Add product to Cart successfully!");
                    } else {
                        alert.close();
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Failed to add the product to the cart. The requested quantity might exceed the stock.");
                    }
                    alert.showAndWait();
                });
            });

            task.setOnFailed(e -> {
                Platform.runLater(() -> {
                    alert.close();
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("An error occurred while processing. Please try again later.");
                    alert.showAndWait();
                });
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

}
