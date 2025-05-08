package sample.hustbookstore.models;

public class Admin {
    private int adminId;
    private String username;
    private String password;
    private String question;
    private String answer;
    private String name;
    private String phoneNumber;
    private String email;

    public Admin(String username, String password, String question, String answer, String name, String phoneNumber, String email, int adminId) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.adminId = adminId;
    }

    public Admin(String username, String password, String question, String answer) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
