package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by ahmed on 8/6/2016.
 */
public class WholeSaleModel {
    private String img;
    private String name;
    private double categoryId;
    private double subCategoryId;
    private double productId;
    private double quantity;
    private double productName;
    private int image;
    public WholeSaleModel() {
    }

    public WholeSaleModel(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public WholeSaleModel(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(double categoryId) {
        this.categoryId = categoryId;
    }

    public double getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(double subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public double getProductId() {
        return productId;
    }

    public void setProductId(double productId) {
        this.productId = productId;
    }
}
