package aykhadma.droidahmed.com.aykhaama.shopping_model;

import java.util.ArrayList;

/**
 * Created by ahmed on 10/31/2016.
 */
public class ShoppingProductListMain {

    private boolean success;
    private Object error;
    private ArrayList<ShoppingProductListItem> data;

    public ShoppingProductListMain() {
    }

    public ShoppingProductListMain(boolean success, Object error, ArrayList<ShoppingProductListItem> data) {
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

    public ArrayList<ShoppingProductListItem> getData() {
        return data;
    }

    public void setData(ArrayList<ShoppingProductListItem> data) {
        this.data = data;
    }
}