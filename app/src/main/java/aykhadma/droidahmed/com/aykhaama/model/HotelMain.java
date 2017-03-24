package aykhadma.droidahmed.com.aykhaama.model;

import java.util.ArrayList;

/**
 * Created by ahmed on 12/26/2016.
 */
public class HotelMain {
    private boolean success;
    private Object error;
    ArrayList<HotelDetailsModel> data;

    public HotelMain() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public ArrayList<HotelDetailsModel> getData() {
        return data;
    }

    public void setData(ArrayList<HotelDetailsModel> data) {
        this.data = data;
    }
}
