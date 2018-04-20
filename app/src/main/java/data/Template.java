package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ListView;


/**
 *
 * @author Shira Elitzur
 */
public class Template implements Serializable {
    private int templateID;
    private String title;
    private int categoryID;
    private List<TemplateChapter> chapters;
    private List<InstitutionDetail> institutionDetails;
    
    public Template() {
        
    }
    
    public Template( int templateID ) {
        setTemplateID(templateID);
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
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

    public List<TemplateChapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<TemplateChapter> chapters) {
        this.chapters = chapters;
    }

	public List<InstitutionDetail> getInstitutionDetails() {
		if(institutionDetails == null) {
			setInstitutionDetails();
		}
		return institutionDetails;
	}

	public void setInstitutionDetails() {
        institutionDetails = new ArrayList<InstitutionDetail>();
        institutionDetails.add(new InstitutionDetail("שם המוסד"));
        institutionDetails.add(new InstitutionDetail("סמל מוסד"));
        institutionDetails.add(new InstitutionDetail("כתובת"));
        institutionDetails.add(new InstitutionDetail("יישוב"));
        institutionDetails.add(new InstitutionDetail("טלפון"));
        institutionDetails.add(new InstitutionDetail("מחוז"));
        institutionDetails.add(new InstitutionDetail("בעלות"));

	}
	
	public void setInstitutionDetails( ArrayList<InstitutionDetail> details ) {
		this.institutionDetails = details;
	}
}
