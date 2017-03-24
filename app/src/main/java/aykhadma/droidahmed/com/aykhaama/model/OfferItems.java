package aykhadma.droidahmed.com.aykhaama.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ahmed on 12/26/2016.
 */
public class OfferItems implements Parcelable {

    private int ID;
    private String Title;
    private String Picture;
    private String Mobile;
    private int Viewed;
    private String CreatedDate;
    private String ModifiedDate;
    private String FinishedDate;
    private int State;



    public OfferItems() {
    }

    protected OfferItems(Parcel in) {
        ID = in.readInt();

        Title = in.readString();
        Mobile = in.readString();

        Picture = in.readString();
         Viewed = in.readInt();
        CreatedDate = in.readString();
        ModifiedDate = in.readString();
        FinishedDate = in.readString();
          State = in.readInt();

    }

    public static final Creator<OfferItems> CREATOR = new Creator<OfferItems>() {
        @Override
        public OfferItems createFromParcel(Parcel in) {
            return new OfferItems(in);
        }

        @Override
        public OfferItems[] newArray(int size) {
            return new OfferItems[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }



    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }


    public int getViewed() {
        return Viewed;
    }

    public void setViewed(int viewed) {
        Viewed = viewed;
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

    public String getFinishedDate() {
        return FinishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        FinishedDate = finishedDate;
    }



    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);

        dest.writeString(Title);

        dest.writeString(Picture);
         dest.writeInt(Viewed);
        dest.writeString(CreatedDate);
        dest.writeString(ModifiedDate);
        dest.writeString(FinishedDate);

         dest.writeInt(State);
        dest.writeString(Mobile);
     }
}
