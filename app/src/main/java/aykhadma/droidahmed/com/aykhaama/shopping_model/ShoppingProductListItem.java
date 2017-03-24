package aykhadma.droidahmed.com.aykhaama.shopping_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ahmed on 10/31/2016.
 */
public class ShoppingProductListItem implements Parcelable{

    private int ID  ;
    private double Code  ;
    private int CategoryID  ;
    private int BrandID  ;
    private double Price  ;
    private double Quantity ;
    private String Picture  ;
    private ArrayList<String> SliderPictures  ;
    private String CreatedDate ;
    private String ModifiedDate  ;
    private int Viewed  ;
    private int Featured  ;
    private int State  ;
    private int ProductID  ;
    private int LanguageID ;
    private String  Name ;
    private String Alias ;
    private String Contents ;
    private String Description ;
    private String Keywords ;
    private String CategoryName ;
    private String  BrandName;

    public ShoppingProductListItem() {
    }

    public ShoppingProductListItem(int ID, int code, int categoryID, int brandID, double price, double quantity,
                                   String picture, ArrayList<String> sliderPictures, String createdDate, String modifiedDate,
                                   int viewed, int featured, int state, int productID, int languageID, String name,
                                   String alias, String contents, String description, String keywords, String categoryName,
                                   String brandName) {
        this.ID = ID;
        Code = code;
        CategoryID = categoryID;
        BrandID = brandID;
        Price = price;
        Quantity = quantity;
        Picture = picture;
        SliderPictures = sliderPictures;
        CreatedDate = createdDate;
        ModifiedDate = modifiedDate;
        Viewed = viewed;
        Featured = featured;
        State = state;
        ProductID = productID;
        LanguageID = languageID;
        Name = name;
        Alias = alias;
        Contents = contents;
        Description = description;
        Keywords = keywords;
        CategoryName = categoryName;
        BrandName = brandName;
    }

    protected ShoppingProductListItem(Parcel in) {
        ID = in.readInt();
        Code = in.readDouble();
        CategoryID = in.readInt();
        BrandID = in.readInt();
        Price = in.readDouble();
        Quantity = in.readDouble();
        Picture = in.readString();
        SliderPictures = in.createStringArrayList();
        CreatedDate = in.readString();
        ModifiedDate = in.readString();
        Viewed = in.readInt();
        Featured = in.readInt();
        State = in.readInt();
        ProductID = in.readInt();
        LanguageID = in.readInt();
        Name = in.readString();
        Alias = in.readString();
        Contents = in.readString();
        Description = in.readString();
        Keywords = in.readString();
        CategoryName = in.readString();
        BrandName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeDouble(Code);
        dest.writeInt(CategoryID);
        dest.writeInt(BrandID);
        dest.writeDouble(Price);
        dest.writeDouble(Quantity);
        dest.writeString(Picture);
        dest.writeStringList(SliderPictures);
        dest.writeString(CreatedDate);
        dest.writeString(ModifiedDate);
        dest.writeInt(Viewed);
        dest.writeInt(Featured);
        dest.writeInt(State);
        dest.writeInt(ProductID);
        dest.writeInt(LanguageID);
        dest.writeString(Name);
        dest.writeString(Alias);
        dest.writeString(Contents);
        dest.writeString(Description);
        dest.writeString(Keywords);
        dest.writeString(CategoryName);
        dest.writeString(BrandName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShoppingProductListItem> CREATOR = new Creator<ShoppingProductListItem>() {
        @Override
        public ShoppingProductListItem createFromParcel(Parcel in) {
            return new ShoppingProductListItem(in);
        }

        @Override
        public ShoppingProductListItem[] newArray(int size) {
            return new ShoppingProductListItem[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getCode() {
        return Code;
    }

    public void setCode(double code) {
        Code = code;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int brandID) {
        BrandID = brandID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public ArrayList<String> getSliderPictures() {
        return SliderPictures;
    }

    public void setSliderPictures(ArrayList<String> sliderPictures) {
        SliderPictures = sliderPictures;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public int getViewed() {
        return Viewed;
    }

    public void setViewed(int viewed) {
        Viewed = viewed;
    }

    public int getFeatured() {
        return Featured;
    }

    public void setFeatured(int featured) {
        Featured = featured;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getLanguageID() {
        return LanguageID;
    }

    public void setLanguageID(int languageID) {
        LanguageID = languageID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }
}
