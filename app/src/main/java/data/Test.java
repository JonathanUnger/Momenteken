package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Test implements Serializable {
	private List<TestChapter> chapters;
	//private List<TestItem> items;
	private Template template;
	private String institutionName;
	private String date;
	private boolean saved;
	private User user;
	
	public Test(Template template, String institutionName, String date) {
		setTemplate(template);
		setInstitutionName(institutionName);
		setDate(date);
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public List<TestChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<TestChapter> chapters) {
		this.chapters = chapters;
	}

	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * initialize the test list of chapters. And then initialize each chapter with new Test
	 * Chapter instance and assign to its template field the matching template from the template's
	 * chapters list
	 */
	public void initChapters() {
		chapters = new ArrayList<TestChapter>(getTemplate().getChapters().size());
		Log.d("","Size: " +chapters.size());
		TestChapter chapter;
		for( int i = 0; i < getTemplate().getChapters().size(); i++ ) {
			chapters.add(i, new TestChapter());
			chapter = chapters.get(i);
			chapter.setTemplate(getTemplate().getChapters().get(i));
			chapter.initItems();
		}
	}
	
	public boolean isValid() {
		if ( chapters == null || chapters.size() == 0 ) {
			return false;
		}
		
		for( TestChapter chapter : getChapters() ) {
			if ( !chapter.isValid() ) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//		public List<TestItem> getItems() {
//		return items;
//	}
//	public void setItems(List<TestItem> items) {
//		this.items = items;
//	}
}
