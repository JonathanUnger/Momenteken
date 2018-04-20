package com.elitzur_software.momentekenmobile;

import java.io.ObjectInputStream.GetField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;
import data.Template;
import data.TemplateChapter;
import data.TemplateItem;
import data.Test;
import data.User;

public class SessionGlobalData {
	
	public final static int SCHOOL_ID = 1;
	public final static int KINDERGARTEN_ID = 2;
	public Test test;
	public User user;
	private ArrayList<User> usersList;
	private User userToDo;
	
	private static volatile SessionGlobalData instance = null;
	/**
	 * The name and one word description of the tested institution.
	 */
	private String institutionTilte = "institution";
	
	/**
	 * The date of the current test.
	 */
	private String testDate; 
	
	/**
	 * 
	 */
	private int templateID;
	
	private Template testTemplate;
	private Template templateDemo;
	
			


	private SessionGlobalData() {
	}
	
	public static SessionGlobalData getInstance() {
        if (instance == null) {
            synchronized (SessionGlobalData.class) {
            	if (instance == null) {
            		instance = new SessionGlobalData();
            		Calendar calendar = Calendar.getInstance();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            		String formattedDate = dateFormat.format(calendar.getTime());
            		instance.setTestDate(formattedDate);
            	}
            }
        }
        return instance;
	}
	
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getInstitutionTilte() {
		return institutionTilte;
	}

	public void setInstitutionTilte(String institutionTilte) {
		this.institutionTilte = institutionTilte;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	
	public int getTemplateID() {
		return templateID;
	}

	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}
	

	public Template getTestTemplate() {
		return testTemplate;
	}

	public void setTestTemplate(Template testTemplate) {
		this.testTemplate = testTemplate;
	}
	
	public void initTest() {
		test = new Test( testTemplate , getInstitutionTilte(), getTestDate() );
		test.setUser(user);
		Log.d("", "InitTest- new Test (template). Template = "+ testTemplate);
		test.initChapters();
	}
	
	public Template getTemplateDemo() {
		return templateDemo;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(ArrayList<User> usersList) {
		this.usersList = usersList;
	}

	public User getUserToDo() {
		return userToDo;
	}

	public void setUserToDo(User userToDo) {
		this.userToDo = userToDo;
	}
	
	
    
}
