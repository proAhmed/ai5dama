package aykhadma.droidahmed.com.aykhaama.wholesale_model;

import java.util.ArrayList;


/**
 * Created by ahmed on 10/31/2016.
 */
public class WholeSaleCategoryMain {

    private boolean success;
    private Object error;
    private ArrayList<WholeSaleCategoryItems> data;

    public WholeSaleCategoryMain() {
    }

    public WholeSaleCategoryMain(boolean success, Object error, ArrayList<WholeSaleCategoryItems> data) {
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

    public ArrayList<WholeSaleCategoryItems> getData() {
        return data;
    }

    public void setData(ArrayList<WholeSaleCategoryItems> data) {
        this.data = data;
    }
}