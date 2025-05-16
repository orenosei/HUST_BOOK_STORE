package sample.hustbookstore.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import sample.hustbookstore.models.BillList;
import sample.hustbookstore.models.UserList;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static sample.hustbookstore.LaunchApplication.localCart;

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

    private LocalDate todayLocalDate = LocalDate.now();
    private Date tabIncomeLeft;
    private Date tabIncomeRight;
    private Date tabOrderLeft;
    private Date tabOrderRight;

    private int tabIncomeId = 0;
    private int tabOrderId = 0;

    public void calculateTabIncome() {
        tabIncomeLeft = Date.valueOf(todayLocalDate.minusWeeks(tabIncomeId + 1));
        tabIncomeRight = Date.valueOf(todayLocalDate.minusWeeks(tabIncomeId));
    }

    public void calculateTabOrder() {
        tabOrderLeft = Date.valueOf(todayLocalDate.minusWeeks(tabOrderId + 1));
        tabOrderRight = Date.valueOf(todayLocalDate.minusWeeks(tabOrderId));
    }

    public void loadCount() {
        customerCount.setText(Integer.toString(UserList.countUser()));
        orderCount.setText(Integer.toString(BillList.countBill()));
        Date today = Date.valueOf(todayLocalDate);
        Date weekLater = Date.valueOf(todayLocalDate.minusWeeks(1));
        incomeCount.setText(String.format("%.2f",BillList.calculateIncome(weekLater,today)));
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

    @FXML
    private void handleLeftIncomeButton(ActionEvent event) {
        if (event.getSource() == leftIncomeButton) {
            tabIncomeId++;
            calculateTabIncome();
            loadIncomeChart(BillList.getIncomeDataByDate(tabIncomeLeft,tabIncomeRight));
        }
    }

    @FXML
    private void handleRightIncomeButton(ActionEvent event) {
        if (event.getSource() == rightIncomeButton && tabIncomeId > 0) {
            tabIncomeId--;
            calculateTabIncome();
            loadIncomeChart(BillList.getIncomeDataByDate(tabIncomeLeft,tabIncomeRight));
        }
    }

    @FXML
    private void handleLeftOrderButton(ActionEvent event) {
        if (event.getSource() == leftOrderButton) {
            tabOrderId++;
            calculateTabOrder();
            loadOrderChart(BillList.getOrderDataByDate(tabOrderLeft,tabOrderRight));
        }
    }

    @FXML
    private void handleRightOrderButton(ActionEvent event) {
        if (event.getSource() == rightOrderButton && tabOrderId > 0) {
            tabOrderId--;
            calculateTabOrder();
            loadOrderChart(BillList.getOrderDataByDate(tabOrderLeft,tabOrderRight));
        }
    }

    @FXML
    private void handleConfirmButton(ActionEvent event) {
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
                incomeCount.setText(String.format("%.2f",BillList.calculateIncome(dayFrom,dayTo)));
            }
        }
    }

    public void initialize(){
        datePickerFrom.setValue(todayLocalDate.minusWeeks(1));
        datePickerTo.setValue(todayLocalDate);
        loadCount();
        calculateTabIncome();
        loadIncomeChart(BillList.getIncomeDataByDate(tabIncomeLeft,tabIncomeRight));
        calculateTabOrder();
        loadOrderChart(BillList.getOrderDataByDate(tabOrderLeft,tabOrderRight));
    }
}
