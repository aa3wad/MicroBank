package models;

public class User {
    private String userName;
    private String accountNumber;
    private String password;

    public User(){

    }

    public User(String userName, String accountNumber, String password) {
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
