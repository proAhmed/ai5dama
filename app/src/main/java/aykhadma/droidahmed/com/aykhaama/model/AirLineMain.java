package aykhadma.droidahmed.com.aykhaama.model;

import java.util.ArrayList;

/**
 * Created by ahmed on 12/26/2016.
 */
public class AirLineMain {
    private boolean success;
    private Object error;
    ArrayList<AirLineModel> data;

    public AirLineMain() {
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

    public ArrayList<AirLineModel> getData() {
        return data;
    }

    public void setData(ArrayList<AirLineModel> data) {
        this.data = data;
    }
}
