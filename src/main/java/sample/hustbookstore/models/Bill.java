package sample.hustbookstore.models;

import java.time.LocalDate;
import java.util.List;

public class Bill {
    private int billId;
    private User user;
    private double totalPrice;
    private double profit;
    private LocalDate purchasedDate;
    private List<BillItem> items;


    public Bill(int billId, double totalPrice, double profit, LocalDate purchasedDate, List<BillItem> items, User user) {
        this.billId = billId;
        this.totalPrice = totalPrice;
        this.profit = profit;
        this.purchasedDate = purchasedDate;
        this.items = items;
        this.user = user;
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }


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
