package com.elitzur_software.momentekenmobile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import data.User;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 
 * @author Shira Elitzur
 *
 */
public class MomentekenApp extends Application {
	
	private boolean firstNewActivity = true;
	private MySQLiteHelper mySQLhelper;
	//private final String PREF_OBJECT_NAME = "MyApplicationPrefs";

	private SharedPreferences sharedPrefs;
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String USER_PRIVILEG = "privileg";
	public static final String USER_ID = "id";

	
	@Override
	public void onCreate() {
		
		super.onCreate();
		setSharedPrefs();
		mySQLhelper = new MySQLiteHelper(this);
		Log.e("momenteken", "Application's onCreate()\n");
	}


	
	public MySQLiteHelper getMySQLhelper() {
		return mySQLhelper;
	}


	public String USER_NAME() {
		return USER_NAME;
	}



	public String PASSWORD() {
		return PASSWORD;
	}



	public SharedPreferences getSharedPrefs() {
		return sharedPrefs;
	}
	
	public void setSharedPrefs() {
		sharedPrefs  = getSharedPreferences("UserData", MODE_PRIVATE);
	}
	
	public void setSharedPrefs_userName( String userName ) {
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(USER_NAME, userName);
		editor.commit();
	}
	
	public void setSharedPrefs_password( String password ) {
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(USER_NAME, password);
		editor.commit();
	}
	
	public void setSharedPrefs_userId( int id ) {
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putInt(USER_ID, id);
		editor.commit();
	}
	
	public void setSharedPrefs_userPrivileg( int privileg ) {
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putInt(USER_PRIVILEG, privileg);
		editor.commit();
	}



	public boolean isFirstNewActivity() {
		return firstNewActivity;
	}



	public void setFirstNewActivity(boolean firstNewActivity) {
		this.firstNewActivity = firstNewActivity;
	}



	public static String getUSER_ID() {
		return USER_ID;
	}



    

}
