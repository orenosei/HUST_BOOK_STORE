package sample.hustbookstore.models;

public class User extends Admin{
    private String address;
    private int userId;


    public User(String username, String password, String question, String answer, String name, String phoneNumber, String email, String address, int userId) {
        super(username, password, question, answer, name, phoneNumber, email);
        this.address = address;
        this.userId = userId;
    }

    public User(String username, String password, String question, String answer, String name, String phoneNumber, String email, String address) {
        super(username, password, question, answer, name, phoneNumber, email);
        this.address = address;
    }

    public User(String username, String password, String question, String answer) {
        super(username, password, question, answer);
    }

    public User() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
