package aykhadma.droidahmed.com.aykhaama.wholesale_model;

import java.util.ArrayList;


/**
 * Created by ahmed on 10/31/2016.
 */
public class WholeSaleProductListMain {

    private boolean success;
    private Object error;
    private ArrayList<WholeSaleProductListItem> data;

    public WholeSaleProductListMain() {
    }

    public WholeSaleProductListMain(boolean success, Object error, ArrayList<WholeSaleProductListItem> data) {
        this.success = success;
        this.error = error;
        this.data = data;
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

    public ArrayList<WholeSaleProductListItem> getData() {
        return data;
    }

    public void setData(ArrayList<WholeSaleProductListItem> data) {
        this.data = data;
    }
}