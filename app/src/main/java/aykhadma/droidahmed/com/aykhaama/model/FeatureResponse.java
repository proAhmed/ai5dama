package aykhadma.droidahmed.com.aykhaama.model;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;

/**
 * Created by ahmed on 4/21/2016.
 */
public class FeatureResponse {
    private boolean success;
    private Object error;
    private ArrayList<ShoppingProductListItem> data;

    public FeatureResponse(boolean success, Object error, ArrayList<ShoppingProductListItem> data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public FeatureResponse() {
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
