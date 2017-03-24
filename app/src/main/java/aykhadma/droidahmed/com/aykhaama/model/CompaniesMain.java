package aykhadma.droidahmed.com.aykhaama.model;

import java.util.ArrayList;

/**
 * Created by ahmed on 12/26/2016.
 */
public class CompaniesMain {
    private boolean success;
    private Object error;
    ArrayList<CompaniesModel> data;

    public CompaniesMain() {
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

    public ArrayList<CompaniesModel> getData() {
        return data;
    }

    public void setData(ArrayList<CompaniesModel> data) {
        this.data = data;
    }
}
