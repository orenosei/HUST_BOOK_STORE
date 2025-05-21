package sample.hustbookstore.controllers.user;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.StackPane;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.hustbookstore.models.BillList;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.models.BookIndexer;
import sample.hustbookstore.models.Cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserDashboardController {

    @FXML
    private Button trendingLeftBtn;
    @FXML
    private AnchorPane trendingPane;
    @FXML
    private Button trendingRightBtn;
    @FXML
    private StackPane trendingStackPane;

    @FXML
    private AnchorPane recommendationPane;

    @FXML
    private Button recommendationLeftBtn;

    @FXML
    private Button recommendationRightBtn;

    @FXML
    private StackPane recommendationStackPane;

    private List<Book> trendingBooks;
    private List<Book> recommendationBooks;

    private List<Node> cardNodes = new ArrayList<>();
    private int currentIndex = 0;

    private Timeline autoSlideTimeline;
    private final long SLIDE_INTERVAL = 4000;

    @FXML
    public void initialize() {
        setupStackPaneClip();
        loadTrendingBooks();
        initializeCards();
        setupAutoSlide();
    }

    private void loadTrendingBooks() {
        BillList billList = new BillList();
        trendingBooks =  billList.getTrendingBooks();
    }

    private void initializeCards() {
        cardNodes.clear();
        trendingStackPane.getChildren().clear();

        try {
            for (Book book : trendingBooks) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/user/user-trendingCard.fxml"));
                Node card = loader.load();
                UserTrendingCardController controller = loader.getController();
                controller.setData(book);
                cardNodes.add(card);
            }

            if (!cardNodes.isEmpty()) {
                trendingStackPane.getChildren().add(cardNodes.get(currentIndex));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupStackPaneClip() {
        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        clip.widthProperty().bind(trendingStackPane.widthProperty());
        clip.heightProperty().bind(trendingStackPane.heightProperty());

        trendingStackPane.setClip(clip);
    }

    private void slideCards(double distance) {
        autoSlideTimeline.pause();

        Node currentCard = trendingStackPane.getChildren().get(0);
        Node newCard = cardNodes.get(currentIndex);
        MotionBlur blur = new MotionBlur();
        blur.setRadius(15);

        currentCard.setEffect(blur);
        newCard.setEffect(blur);

        newCard.setTranslateX(distance);
        trendingStackPane.getChildren().add(newCard);

        TranslateTransition currentTransition = new TranslateTransition(Duration.millis(700), currentCard);
        currentTransition.setByX(-distance);

        TranslateTransition newTransition = new TranslateTransition(Duration.millis(700), newCard);
        newTransition.setFromX(distance);
        newTransition.setToX(0);

        trendingLeftBtn.setDisable(true);
        trendingRightBtn.setDisable(true);

        newTransition.setOnFinished(e -> {
            trendingStackPane.getChildren().remove(currentCard);
            trendingLeftBtn.setDisable(false);
            trendingRightBtn.setDisable(false);
            newCard.setEffect(null);

            autoSlideTimeline.playFromStart();
        });

        currentTransition.play();
        newTransition.play();
    }

    private void setupAutoSlide() {
        autoSlideTimeline = new Timeline(
                new KeyFrame(Duration.millis(SLIDE_INTERVAL), e -> {
                    if (!cardNodes.isEmpty()) {
                        currentIndex = (currentIndex + 1) % cardNodes.size();
                        slideCards(1100);
                    }
                }));
        autoSlideTimeline.setCycleCount(Timeline.INDEFINITE);
        autoSlideTimeline.play();
    }

    @FXML
    private void handleTrendingLeftBtn() {
        if (cardNodes.isEmpty() || trendingLeftBtn.isDisabled()) return;
        currentIndex = (currentIndex - 1 + cardNodes.size()) % cardNodes.size();
        slideCards(-1100);
    }

    @FXML
    private void handleTrendingRightBtn() {
        if (cardNodes.isEmpty() || trendingRightBtn.isDisabled()) return;
        currentIndex = (currentIndex + 1) % cardNodes.size();
        slideCards(1100);
    }

    public void handleRecommendationLeftBtn() {
    }

    public void handleRecommendationRightBtn() {

    }

    // // todo: Recommendation
    // Cho recommend
    //WIP
//    private void loadRecommendationBooks() {
//        BillList billList = new BillList();
//        recommendationBooks =  billList.getRecommendationBooks();
//    }

}