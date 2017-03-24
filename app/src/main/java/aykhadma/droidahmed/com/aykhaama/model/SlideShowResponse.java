package aykhadma.droidahmed.com.aykhaama.model;

import java.util.ArrayList;

/**
 * Created by ahmed on 4/21/2016.
 */
public class SlideShowResponse {
    private boolean success;
    private Object error;
    private ArrayList<SlideShow> data;

    public SlideShowResponse(boolean success, Object error, ArrayList<SlideShow> data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public SlideShowResponse() {
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

    public ArrayList<SlideShow> getData() {
        return data;
    }

    public void setData(ArrayList<SlideShow> data) {
        this.data = data;
    }
}
