package sample.hustbookstore.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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

    public void loadCount() {
        customerCount.setText(Integer.toString(UserList.countUser()));
        orderCount.setText(Integer.toString(BillList.countBill()));
        Date today = Date.valueOf(LocalDate.now());
        Date weekLater = Date.valueOf(LocalDate.now().minusWeeks(1));
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

    public void initialize(){
        loadCount();
        loadIncomeChart(BillList.getIncomeDataByDate());
        loadOrderChart(BillList.getOrderDataByDate());
    }
}
