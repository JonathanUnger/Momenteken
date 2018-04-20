package com.elitzur_software.momentekenmobile;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class UIUtils {
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
}
