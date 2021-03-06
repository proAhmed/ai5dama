package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 4/21/2016.
 */
public class UserSignOutResponse {
    private boolean success;
    private Object error;
    private String data;

    public UserSignOutResponse(boolean success, Object error, String data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public UserSignOutResponse() {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
