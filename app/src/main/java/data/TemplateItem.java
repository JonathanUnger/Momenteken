/*
 * 
 */

package data;

import java.io.Serializable;

/**
 *
 * @author Shira Elitzur
 */
public class TemplateItem implements Serializable {

    public static final String[] itemTitles = { "מס סעיף","כותרת","תיאור",
            "תחום","סמכות מאשרת","רמה",
            "תדירות"};

    private int templateItemID;
    private String itemNumber;
    private int itemLevel;
    private String title;
    private String description;
    private String area;
    private String authority;
    private String frequency;
    private int chapterID;

    public int getTemplateItemID() {
        return templateItemID;
    }

    public void setTemplateItemID(int templateItemID) {
        this.templateItemID = templateItemID;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    @Override
    public String toString() {
        return "ID: " + getTemplateItemID() +
                "\nNumber: " + getItemNumber() +
                "\nTitle: " + getTitle() +
                "\nDescription: " + getDescription();
    }

    
    
    
}
