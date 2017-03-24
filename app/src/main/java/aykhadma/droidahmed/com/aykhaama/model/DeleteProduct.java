package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 4/5/2016.
 */
public class DeleteProduct {
    private boolean success;
    private String error;
    private String data;

    public DeleteProduct() {
    }

    public DeleteProduct(boolean success, String error, String data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public boolean getSuccess() {
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
