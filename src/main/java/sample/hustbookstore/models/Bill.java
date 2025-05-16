package sample.hustbookstore.models;

import java.time.LocalDate;

public class Bill {
    private int userID;
    private double totalPrice;
    private double profit;
    private LocalDate purchasedDate;

    public Bill(int userID, double totalPrice, double profit, LocalDate purchasedDate) {
        this.userID = userID;
        this.totalPrice = totalPrice;
        this.profit = profit;
        this.purchasedDate = purchasedDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }
}
