package aykhadma.droidahmed.com.aykhaama.wholesale_model;



/**
 * Created by ahmed on 10/31/2016.
 */
public class WholeSaleProductMain {

    private boolean success;
    private Object error;
    private WholeSaleProductListItem data;

    public WholeSaleProductMain() {
    }

    public WholeSaleProductMain(boolean success, Object error, WholeSaleProductListItem data) {
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

    public WholeSaleProductListItem getData() {
        return data;
    }

    public void setData(WholeSaleProductListItem data) {
        this.data = data;
    }
}