package sample.hustbookstore.controllers.user;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.hustbookstore.models.*;
import sample.hustbookstore.utils.dao.BillList;

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
    private Button recommendLeftBtn;

    @FXML
    private Button recommendRightBtn;

    @FXML
    private StackPane recommendStackPane;

    private List<Book> trendingBooks;
    private List<Book> recommendBooks;

    private List<Node> cardNodesTrending = new ArrayList<>();
    private List<Node> cardNodesRecommend = new ArrayList<>();
    private int currentIndexTrending = 0;
    private int currentIndexRecommend = 0;

    private Timeline autoSlideTimelineTrending;
    private Timeline autoSlideTimelineRecommend;
    private final long SLIDE_INTERVAL_TRENDING = 4000;
    private final long SLIDE_INTERVAL_RECOMMEND = 4000;

    @FXML
    public void initialize() throws Exception {
        setupStackPaneClip();
        loadTrendingBooks();
        loadRecommendBooks();
        initializeTrendingCards();
        initializeRecommendCards();
        setupAutoSlideTrending();
        setupAutoSlideRecommend();
    }

    private void loadTrendingBooks() {
        trendingBooks =  BillList.getTrendingBooks();
    }

    private void loadRecommendBooks() throws Exception {
        recommendBooks = BillList.getRecommendBooks();
    }

    private void initializeTrendingCards() {
        cardNodesTrending.clear();
        trendingStackPane.getChildren().clear();

        try {
            for (Book book : trendingBooks) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/user/user-trendingCard.fxml"));
                Node card = loader.load();
                UserTrendingCardController controller = loader.getController();
                controller.setData(book);
                cardNodesTrending.add(card);
            }

            if (!cardNodesTrending.isEmpty()) {
                trendingStackPane.getChildren().add(cardNodesTrending.get(currentIndexTrending));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeRecommendCards() {
        cardNodesRecommend.clear();
        recommendStackPane.getChildren().clear();

        try {
            for (Book book : recommendBooks) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/hustbookstore/user/user-recommendCard.fxml"));
                Node card = loader.load();
                UserRecommendCardController controller = loader.getController();
                controller.setData(book);
                cardNodesRecommend.add(card);
            }

            if (!cardNodesRecommend.isEmpty()) {
                recommendStackPane.getChildren().add(cardNodesRecommend.get(currentIndexRecommend));
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

        clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        clip.widthProperty().bind(recommendStackPane.widthProperty());
        clip.heightProperty().bind(recommendStackPane.heightProperty());
        recommendStackPane.setClip(clip);
    }

    private void slideCardsTrending(double distance) {
        autoSlideTimelineTrending.pause();

        Node currentCard = trendingStackPane.getChildren().get(0);
        Node newCard = cardNodesTrending.get(currentIndexTrending);
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

            autoSlideTimelineTrending.playFromStart();
        });

        currentTransition.play();
        newTransition.play();
    }

    private void slideCardsRecommend(double distance) {
        autoSlideTimelineRecommend.pause();

        Node currentCard = recommendStackPane.getChildren().get(0);
        Node newCard = cardNodesRecommend.get(currentIndexRecommend);
        MotionBlur blur = new MotionBlur();
        blur.setRadius(15);

        currentCard.setEffect(blur);
        newCard.setEffect(blur);

        newCard.setTranslateX(distance);
        recommendStackPane.getChildren().add(newCard);

        TranslateTransition currentTransition = new TranslateTransition(Duration.millis(700), currentCard);
        currentTransition.setByX(-distance);

        TranslateTransition newTransition = new TranslateTransition(Duration.millis(700), newCard);
        newTransition.setFromX(distance);
        newTransition.setToX(0);

        recommendLeftBtn.setDisable(true);
        recommendRightBtn.setDisable(true);

        newTransition.setOnFinished(e -> {
            recommendStackPane.getChildren().remove(currentCard);
            recommendLeftBtn.setDisable(false);
            recommendRightBtn.setDisable(false);
            newCard.setEffect(null);

            autoSlideTimelineRecommend.playFromStart();
        });

        currentTransition.play();
        newTransition.play();
    }

    private void setupAutoSlideTrending() {
        autoSlideTimelineTrending = new Timeline(
                new KeyFrame(Duration.millis(SLIDE_INTERVAL_TRENDING), e -> {
                    if (!cardNodesTrending.isEmpty()) {
                        currentIndexTrending = (currentIndexTrending + 1) % cardNodesTrending.size();
                        slideCardsTrending(1100);
                    }
                }));
        autoSlideTimelineTrending.setCycleCount(Timeline.INDEFINITE);
        autoSlideTimelineTrending.play();
    }

    private void setupAutoSlideRecommend() {
        autoSlideTimelineRecommend = new Timeline(
                new KeyFrame(Duration.millis(SLIDE_INTERVAL_RECOMMEND), e -> {
                    if (!cardNodesRecommend.isEmpty()) {
                        currentIndexRecommend = (currentIndexRecommend + 1) % cardNodesRecommend.size();
                        slideCardsRecommend(1100);
                    }
                }));
        autoSlideTimelineRecommend.setCycleCount(Timeline.INDEFINITE);
        autoSlideTimelineRecommend.play();
    }

    @FXML
    public void handleTrendingLeftBtn() {
        if (cardNodesTrending.isEmpty() || trendingLeftBtn.isDisabled()) return;
        currentIndexTrending = (currentIndexTrending - 1 + cardNodesTrending.size()) % cardNodesTrending.size();
        slideCardsTrending(-1100);
    }

    @FXML
    public void handleTrendingRightBtn() {
        if (cardNodesTrending.isEmpty() || trendingRightBtn.isDisabled()) return;
        currentIndexTrending = (currentIndexTrending + 1) % cardNodesTrending.size();
        slideCardsTrending(1100);
    }

    @FXML
    public void handleRecommendLeftBtn() {
        if (cardNodesRecommend.isEmpty() || recommendLeftBtn.isDisabled()) return;
        currentIndexRecommend = (currentIndexRecommend - 1 + cardNodesRecommend.size()) % cardNodesRecommend.size();
        slideCardsRecommend(-1100);
    }

    @FXML
    public void handleRecommendRightBtn() {
        if (cardNodesRecommend.isEmpty() || recommendRightBtn.isDisabled()) return;
        currentIndexRecommend = (currentIndexRecommend + 1) % cardNodesRecommend.size();
        slideCardsRecommend(1100);
    }

}