package aykhadma.droidahmed.com.aykhaama.model;

/**
 * Created by Hong Thai.
 */
public class SlidingMenuItem {

    private int imageResource;
    private String menuItemName;
    private int positions;

    public SlidingMenuItem(int image, String name, int positions) {
        this.imageResource = image;
        this.menuItemName = name;
        this.positions = positions;
    }

    public SlidingMenuItem( String menuItemName, int positions) {
         this.menuItemName = menuItemName;
        this.positions = positions;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public int getPositions() {
        return positions;
    }

    public void setPositions(int positions) {
        this.positions = positions;
    }
}
