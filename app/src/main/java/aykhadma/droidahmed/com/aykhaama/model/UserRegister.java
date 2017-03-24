package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 9/4/2016.
 */
public class UserRegister {

   private int ID;
   private String FullName;
    private String UserName;
    private String Picture;
    private String Email;
    private String Mobile;
    private String City;

    public UserRegister() {
    }

    public UserRegister(int ID, String fullName, String userName, String picture, String email, String mobile, String city) {
        this.ID = ID;
        FullName = fullName;
        UserName = userName;
        Picture = picture;
        Email = email;
        Mobile = mobile;
        City = city;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
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
}
