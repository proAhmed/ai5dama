package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 8/10/2016.
 */
public class ShoppingModel {
    private String name;
     private String img;
    private int images;


    public ShoppingModel(int images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
