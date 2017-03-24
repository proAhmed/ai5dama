package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 8/5/2016.
 */
public class HotelModel {
    private String name;
    private String rate;
    private String img;
    private String room;
    private String checkIn;
    private String checkOut;
    private String bookingNow;
    private int favourite;
    private int cities;
    private int image;
    public HotelModel() {
    }
    public HotelModel(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getBookingNow() {
        return bookingNow;
    }

    public void setBookingNow(String bookingNow) {
        this.bookingNow = bookingNow;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }
}
