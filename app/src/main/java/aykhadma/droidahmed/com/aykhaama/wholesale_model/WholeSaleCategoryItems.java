package aykhadma.droidahmed.com.aykhaama.wholesale_model;

/**
 * Created by ahmed on 10/31/2016.
 */
public class WholeSaleCategoryItems {

   private int  ID;
    private String ParentID;
    private int Level;
   private String Picture;
    private int SortingOrder;
    private int Featured;
    private String CreatedDate;
    private String ModifiedDate;
    private int State;
    private int CategoryID;
    private int LanguageID;
    private String Name;
    private String Alias;
    private String Description;
    private String Keywords;
    private boolean IsParent;

    public WholeSaleCategoryItems() {
    }

    public WholeSaleCategoryItems(int ID, String parentID, int level, String picture, int sortingOrder,
                                  int featured, String createdDate, String modifiedDate, int state,
                                  int categoryID, int languageID, String name, String alias, String description,
                                  String keywords, boolean isParent) {
        this.ID = ID;
        ParentID = parentID;
        Level = level;
        Picture = picture;
        SortingOrder = sortingOrder;
        Featured = featured;
        CreatedDate = createdDate;
        ModifiedDate = modifiedDate;
        State = state;
        CategoryID = categoryID;
        LanguageID = languageID;
        Name = name;
        Alias = alias;
        Description = description;
        Keywords = keywords;
        IsParent = isParent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public int getSortingOrder() {
        return SortingOrder;
    }

    public void setSortingOrder(int sortingOrder) {
        SortingOrder = sortingOrder;
    }

    public int getFeatured() {
        return Featured;
    }

    public void setFeatured(int featured) {
        Featured = featured;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getLanguageID() {
        return LanguageID;
    }

    public void setLanguageID(int languageID) {
        LanguageID = languageID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public boolean isParent() {
        return IsParent;
    }

    public void setParent(boolean parent) {
        IsParent = parent;
    }
}
