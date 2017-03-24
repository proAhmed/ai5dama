package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 4/21/2016.
 */
public class UserRegisterResponse {
    private boolean success;
    private Object error;
    private UserRegister data;

    public UserRegisterResponse(boolean success, Object error, UserRegister data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public UserRegisterResponse() {
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

    public UserRegister getData() {
        return data;
    }

    public void setData(UserRegister data) {
        this.data = data;
    }
}
