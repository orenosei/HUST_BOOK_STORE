package sample.hustbookstore.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty address;
    private final DoubleProperty total;

    public Customer(String id, String name, String phone, String address, double total) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.total = new SimpleDoubleProperty(total);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getPhone() { return phone.get(); }
    public String getAddress() { return address.get(); }
    public double getTotal() { return total.get(); }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty addressProperty() { return address; }
    public DoubleProperty totalProperty() { return total; }
}

