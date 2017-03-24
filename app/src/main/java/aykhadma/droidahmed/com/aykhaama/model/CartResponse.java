package aykhadma.droidahmed.com.aykhaama.model;

import java.util.ArrayList;

/**
 * Created by ahmed on 12/3/2016.
 */
public class CartResponse {

  private boolean success;
  private String error;
  private ArrayList<CartItem> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<CartItem> getData() {
        return data;
    }

    public void setData(ArrayList<CartItem> data) {
        this.data = data;
    }

    public CartResponse() {
    }
}
