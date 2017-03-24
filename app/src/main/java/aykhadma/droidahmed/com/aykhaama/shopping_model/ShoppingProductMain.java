package aykhadma.droidahmed.com.aykhaama.shopping_model;

import java.util.ArrayList;

/**
 * Created by ahmed on 10/31/2016.
 */
public class ShoppingProductMain {

    private boolean success;
    private Object error;
    private ShoppingProductListItem data;

    public ShoppingProductMain() {
    }

    public ShoppingProductMain(boolean success, Object error,ShoppingProductListItem data) {
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

    public ShoppingProductListItem getData() {
        return data;
    }

    public void setData(ShoppingProductListItem data) {
        this.data = data;
    }
}