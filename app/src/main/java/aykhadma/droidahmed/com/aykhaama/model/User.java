package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 8/27/2016.
 */
public class User {
    private String UserName;
    private String Password;
    private String Email;
    private String Mobile;
    private String City;
    private String Country;
    private String Picture;
    private String FullName;
    private String cardNumber;

    public User() {
    }

    public User(String fullName, String userName, String password, String email, String mobile, String city) {
        UserName = userName;
        Password = password;
        Email = email;
        Mobile = mobile;
        City = city;
        FullName = fullName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", City='" + City + '\'' +
                ", Picture='" + Picture + '\'' +
                ", FullName='" + FullName + '\'' +
                '}';
    }
}
