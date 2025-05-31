package sample.hustbookstore.models;

public class User extends Admin{
    private String address;
    private int userId;
    private boolean isBanned;


    public User(String username, String password, String question, String answer, String name, String phoneNumber, String email, String address, int userId, boolean isBanned) {
        super(username, password, question, answer, name, phoneNumber, email);
        this.address = address;
        this.userId = userId;
        this.isBanned = isBanned;
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

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

}
