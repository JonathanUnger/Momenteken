/*
 * 
 */

package data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Shira Elitzur
 */
public class TemplateChapter implements Serializable {
    private int chapterID;
    private String title;
    private int categoryID;
    private int templateID;
    private List<TemplateItem> items;

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }

    public List<TemplateItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateItem> items) {
        this.items = items;
    }
    
    
}
