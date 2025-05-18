package sample.hustbookstore.controllers.user;


import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.StackPane;

import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.Cart;

import java.io.IOException;
import java.util.List;

public class UserDashboardController {

    @FXML
    private AnchorPane recommendationPane;
    @FXML
    private Button trendingLeftBtn;
    @FXML
    private AnchorPane trendingPane;
    @FXML
    private Button trendingRightBtn;
    @FXML
    private StackPane trendingStackPane;
    @FXML
    private Button stopBtn;

    private int currentIndex = 0;
    private Timeline autoSlideTimeline;
    private boolean isAutoSlideEnabled = true;
    private TranslateTransition currentTransition;
    private List<Book> trendingBooks;

    @FXML
    public void initialize() {
        //loadTrendingBooks();
        //initializeCards();
    }

    private void loadTrendingBooks() {
        Cart cart = new Cart();
        trendingBooks = cart.getTrendingBooks();
        if (trendingBooks.size() < 5) {}
    }

    private void initializeCards() {
        trendingStackPane.getChildren().clear();

        try {
            for (Book book : trendingBooks) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/user/user-trendingCard.fxml"));
                Node card = loader.load();

                UserTrendingCardController cardController = loader.getController();
                cardController.setData(book);

                trendingStackPane.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleTrendingLeftBtn() {

    }

    @FXML
    private void handleTrendingRightBtn() {

    }

    @FXML
    private void handleStopBtn() {

    }
}