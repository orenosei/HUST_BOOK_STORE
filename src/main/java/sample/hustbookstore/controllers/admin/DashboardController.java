package sample.hustbookstore.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.hustbookstore.utils.dao.BillListDAO;
import sample.hustbookstore.models.Book;
import sample.hustbookstore.utils.dao.UserListDAO;
import sample.hustbookstore.utils.cacheHandler.ImageCache;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class DashboardController {
    @FXML
    private BarChart<String, Integer> orderChart;

    @FXML
    private Text customerCount;

    @FXML
    private LineChart<String, Float> incomeChart;

    @FXML
    private Text incomeCount;

    @FXML
    private Text orderCount;

    @FXML
    private Button leftIncomeButton;

    @FXML
    private Button rightIncomeButton;

    @FXML
    private Button leftOrderButton;

    @FXML
    private Button rightOrderButton;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private Button confirmButton;

    @FXML
    private Text trendingId;

    @FXML
    private ImageView trendingImage;

    @FXML
    private Text trendingStock;

    @FXML
    private Text trendingPrice;

    @FXML
    private Button leftTrendingButton;

    @FXML
    private Button rightTrendingButton;

    private LocalDate todayLocalDate = LocalDate.now();
    private Date tabIncomeLeft;
    private Date tabIncomeRight;
    private Date tabOrderLeft;
    private Date tabOrderRight;

    private int tabIncomeId = 0;
    private int tabOrderId = 0;

    private List<Book> trendingBooks;
    private Book trendingBook;


    private int tabTrendingId = 0;

    public void calculateTabIncome() {
        tabIncomeLeft = Date.valueOf(todayLocalDate.minusWeeks(tabIncomeId + 1));
        tabIncomeRight = Date.valueOf(todayLocalDate.minusWeeks(tabIncomeId));
    }

    public void calculateTabOrder() {
        tabOrderLeft = Date.valueOf(todayLocalDate.minusWeeks(tabOrderId + 1));
        tabOrderRight = Date.valueOf(todayLocalDate.minusWeeks(tabOrderId));
    }

    public void loadCount() {
        customerCount.setText(Integer.toString(UserListDAO.countUser()));
        orderCount.setText(Integer.toString(BillListDAO.countBill()));
        Date today = Date.valueOf(todayLocalDate);
        Date weekLater = Date.valueOf(todayLocalDate.minusWeeks(1));
        incomeCount.setText(String.format("%.2f", BillListDAO.calculateIncome(weekLater,today)));
    }

    public void loadIncomeChart(List<XYChart.Data<String, Float>> dataList) {
        incomeChart.getData().clear();
        XYChart.Series<String, Float> series = new XYChart.Series();
        series.getData().addAll(dataList);
        incomeChart.getData().add(series);
    }

    public void loadOrderChart(List<XYChart.Data<String, Integer>> dataList) {
        orderChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.getData().addAll(dataList);
        orderChart.getData().add(series);
    }

    public void loadTrendingBooks() {
        trendingBooks =  BillListDAO.getTrendingBooks();
    }

    public void displayTrendingBooks() {
        if (trendingBooks.size() > 0) {
            trendingBook = trendingBooks.get(tabTrendingId);
            trendingId.setText(trendingBook.getID());
            trendingStock.setText(Integer.toString(trendingBook.getStock()));
            trendingPrice.setText(Double.toString(trendingBook.getSellPrice()));

            String imagePath = trendingBook.getImage();
            Image image = ImageCache.loadImage(imagePath);
            trendingImage.setImage(image);
        }
    }

    @FXML
    public void handleLeftIncomeButton(ActionEvent event) {
        if (event.getSource() == leftIncomeButton) {
            tabIncomeId++;
            calculateTabIncome();
            loadIncomeChart(BillListDAO.getIncomeDataByDate(tabIncomeLeft,tabIncomeRight));
        }
    }

    @FXML
    public void handleRightIncomeButton(ActionEvent event) {
        if (event.getSource() == rightIncomeButton && tabIncomeId > 0) {
            tabIncomeId--;
            calculateTabIncome()   ;
            loadIncomeChart(BillListDAO.getIncomeDataByDate(tabIncomeLeft,tabIncomeRight));
        }
    }

    @FXML
    public void handleLeftOrderButton(ActionEvent event) {
        if (event.getSource() == leftOrderButton) {
            tabOrderId++;
            calculateTabOrder();
            loadOrderChart(BillListDAO.getOrderDataByDate(tabOrderLeft,tabOrderRight));
        }
    }

    @FXML
    public void handleRightOrderButton(ActionEvent event) {
        if (event.getSource() == rightOrderButton && tabOrderId > 0) {
            tabOrderId--;
            calculateTabOrder();
            loadOrderChart(BillListDAO.getOrderDataByDate(tabOrderLeft,tabOrderRight));
        }
    }

    @FXML
    public void handleLeftTrendingButton(ActionEvent event) {
        if (event.getSource() == leftTrendingButton) {
            if (tabTrendingId < (trendingBooks.size() - 1)) {
                tabTrendingId++;
            } else {
                tabTrendingId = 0;
            }
            displayTrendingBooks();
        }
    }

    @FXML
    public void handleRightTrendingButton(ActionEvent event) {
        if (event.getSource() == rightTrendingButton) {
            if (tabTrendingId > 0) {
                tabTrendingId--;
            } else {
                tabTrendingId = (trendingBooks.size() - 1);
            }
            displayTrendingBooks();
        }
    }

    @FXML
    public void handleConfirmButton(ActionEvent event) {
        if (event.getSource() == confirmButton) {
            if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields.");
                alert.showAndWait();
            } else if (datePickerFrom.getValue().isAfter(datePickerTo.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose the date order correctly.");
                alert.showAndWait();
            } else {
                Date dayFrom = Date.valueOf(datePickerFrom.getValue());
                Date dayTo = Date.valueOf(datePickerTo.getValue());
                incomeCount.setText(String.format("%.2f", BillListDAO.calculateIncome(dayFrom,dayTo)));
            }
        }
    }

    public void initialize(){
        datePickerFrom.setValue(todayLocalDate.minusWeeks(1));
        datePickerTo.setValue(todayLocalDate);
        loadCount();
        calculateTabIncome();
        loadIncomeChart(BillListDAO.getIncomeDataByDate(tabIncomeLeft,tabIncomeRight));
        calculateTabOrder();
        loadOrderChart(BillListDAO.getOrderDataByDate(tabOrderLeft,tabOrderRight));
        loadTrendingBooks();
        displayTrendingBooks();
    }
}
