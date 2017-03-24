package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 11/30/2016.
 */
public class ResponseAddProduct {

   private boolean  success;
   private String error;
   private String data;

    public ResponseAddProduct() {
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
