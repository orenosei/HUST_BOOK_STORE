package sample.hustbookstore.models;

import sample.hustbookstore.utils.dao.UserList;

import java.time.LocalDate;
import java.util.List;

public class Bill {
    private int billId;
    private int userID;
    private User user;
    private double totalPrice;
    private double profit;
    private LocalDate purchasedDate;
    private List<BillItem> items;


    public Bill(int billId, int userID, double totalPrice, double profit, LocalDate purchasedDate, List<BillItem> items) {
        this.billId = billId;
        this.userID = userID;
        this.totalPrice = totalPrice;
        this.profit = profit;
        this.purchasedDate = purchasedDate;
        this.items = items;
        this.user = UserList.getUserFromId(userID);
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

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

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
