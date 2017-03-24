package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 9/19/2016.
 */
public class FavoriteResponse {

    private String error;
    private boolean success;
    private String data;

    public FavoriteResponse() {
    }

    public FavoriteResponse(String error, boolean success, String data) {
        this.error = error;
        this.success = success;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
