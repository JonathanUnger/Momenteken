package com.elitzur_software.momentekenmobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.elitzur_software.momentekenmobile.R;

import data.Template;
import data.Test;
import data.User;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

public class NewTestActivity extends ActionBarActivity implements TaskCycle {

    /**
     * The test info instance (singleton).
     */
    private SessionGlobalData sessionGlobalData;
    private MomentekenApp application;

    private SharedPreferences sharedPrefs;
    /**
     * From this edit text i will get the institution name that the user insert
     */
    private EditText institutionEdit;
    /**
     * In this edit text the current date is shown and the user can change it
     */
    private static TextView dateEdit;
    /**
     * Spinner for the tests type. The user choose test type and the test he will get
     * will be from the template that match the type he chose
     */
    private Spinner typesSpinner;

    /**
     * The text views that leads the user how to fill the edit fields. I need it in the code
     * to manipulate its font
     */
    private TextView insertTypeTextView;
    /**
     * The text views that leads the user how to fill the edit fields. I need it in the code
     * to manipulate its font
     */
    private TextView insertInstitutionTextView;
    /**
     * The text views that leads the user how to fill the edit fields. I need it in the code
     * to manipulate its font
     */
    private TextView insertDateTextView;

    /**
     * Typeface for the text views font
     */
    private Typeface textViewFont;
    /**
     * The Navigation drawer ListView
     */
    private ListView drawerListView;
    /**
     * List of Navigation items for the drawer list adapter
     */
    private List<NavigationItem> items;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private TextView drawerHeaderTitle;
    private TextView drawerHeaderDate;
    private String title;
    private String instiTitle;
    private Button start;
    private ProgressBar progressBar;
    private String templateType;
    public static final int OPEN_SAVED_TESTS_TASK = 444;

    //private static MySQLiteHelper mySQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        setTouchListener(findViewById(R.id.main_drawer_layout));
        title = getTitle().toString();
        sessionGlobalData = SessionGlobalData.getInstance();
        application = (MomentekenApp)getApplication();
        final MySQLiteHelper mySql = application.getMySQLhelper();
        sharedPrefs = application.getSharedPrefs();

        // if the login activity was skipped because there was user data in the shared preferences
        // so i pull the stored data and assign it to the global object's user object
        if( sessionGlobalData.getUser() == null ) {
            User user = new User();
            user.setID( sharedPrefs.getInt(MomentekenApp.getUSER_ID(), 0));
            user.setPrivilegeLevel(sharedPrefs.getInt(MomentekenApp.USER_PRIVILEG, 0));
            user.setUserName( sharedPrefs.getString(MomentekenApp.USER_NAME, ""));
            user.setPassword(sharedPrefs.getString(MomentekenApp.PASSWORD, ""));
            sessionGlobalData.setUser(user);
        }

        if( sessionGlobalData.getTest() == null ) {
            if(mySql.getTestsList().size() > 0 ) {
                sessionGlobalData.setTest(mySql.getTestsList().get(0));
            }
        }


        getComponents();
        dateEdit.setText(sessionGlobalData.getTestDate());

        initActionBarAndDrawer();

    }

    @Override
    protected void onResume() {

        //institutionEdit.setText("");
        // Open the drawer in the first launch of this activity

        // I decided not to present the drawer opened at first launch but i am leaving it
        // here because i am not sure about that

//		if ( firstLaunch ) {
//			mDrawerLayout.openDrawer(drawerListView);
//			firstLaunch = false;
//
        // Hide the keyboard
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
//		}


        progressBar.setVisibility(ProgressBar.INVISIBLE);
        dateEdit.setEnabled(true);
        institutionEdit.setEnabled(true);
        typesSpinner.setEnabled(true);
        drawerListView.setEnabled(true);
        start.setEnabled(true);



        super.onResume();
    }

    private void initActionBarAndDrawer() {

		/* The Navigation Drawer Setting:
		  	In the test activity the navigation display a list
		  	of operations for this specific test
		 */

        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        items = new ArrayList<NavigationItem>();
        initNavigationItemsList();

        drawerListView = (ListView)findViewById(R.id.left_drawer);
        // the drawer's header definition:
        // first set ad header
        ViewGroup mTop = (ViewGroup)getLayoutInflater().inflate(R.layout.main_drawer_header, drawerListView, false);
        drawerListView.addHeaderView(mTop, null, false);
        // then find the views and set the text to the test's details
        drawerHeaderTitle = (TextView)mTop.findViewById(R.id.headerTitle);
        // sets the header title to display the username
        String name = sharedPrefs.getString(application.USER_NAME(), "");
//		if( sessionGlobalData.getUser().getPrivilegeLevel() == 1 ) {
//			name = name + "\nמנהל";
//		}
        drawerHeaderTitle.setText(null); // for now
        // and the date
        drawerHeaderDate = (TextView)mTop.findViewById(R.id.dateHeaderTextView);
        drawerHeaderDate.setText(SessionGlobalData.getInstance().getTestDate());
        // sign the current page as the selected item in the list
        drawerListView.setItemChecked(1, true);
        NavigationItemAdapter adapter = new NavigationItemAdapter(this,
                R.layout.navigation_drawer_item,
                items);
        drawerListView.setAdapter(adapter);

        drawerListView.setOnItemClickListener( new NavItemListener());

        drawerListView.setItemChecked(1, true);


        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.drawer_ic,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setTitle(" "+title+" ");

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // a nice trick for customizing the action bar title's color
                // without finding the title's view id:
                // String title = " "+getResources().getString(R.string.test_drawer_title)+" ";
                // setTitle((Html.fromHtml("<font color=\"#FF4444\">" + title + "</font>")));

                // i used spaces instead of left padding (that didn't work) between the title
                // and the icon. Not so elegant... but it works :)
                setTitle(" "+getResources().getString(R.string.main_drawer_title)+" ");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(toggle);
        // These two lines set the app icon in the action bar
        //        getSupportActionBar().setHomeButtonEnabled(true);
        //        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // setting the up button, mine is customized and open the navigation drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.drawer_ic));
        //getSupportActionBar().setIcon(R.drawable.drawer_ic);
        toggle.setDrawerIndicatorEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        // essential for opening the drawer when clicking on app icon!
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getComponents() {
        institutionEdit = (EditText)findViewById(R.id.institutionNameEdit);
        dateEdit = (TextView)findViewById(R.id.dateEditView);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }

        });
        typesSpinner = (Spinner)findViewById(R.id.typesSpinner);
        insertTypeTextView = (TextView)findViewById(R.id.insertTypeText);
        insertInstitutionTextView = (TextView)findViewById(R.id.insertInstitutionText);
        insertDateTextView = (TextView)findViewById(R.id.insertDateText);

        //textViewFont = Typeface.createFromAsset(getAssets(), "font.ttf");

        //insertTypeTextView.setTypeface(textViewFont);
        //insertInstitutionTextView.setTypeface(textViewFont);
        //insertDateTextView.setTypeface(textViewFont);


        start = (Button)findViewById(R.id.startTestBtn);

        progressBar = (ProgressBar)findViewById(R.id.progress);
    }

    private void initNavigationItemsList() {
        String[] stringItems = getResources().getStringArray(R.array.menuOperations);
        int[] icons = { R.drawable.new_test,
                R.drawable.test,
                R.drawable.details,
						/*R.drawable.logout*/};
        // https://icons8.com/ in the about
        int i = -1;
        for( String string : stringItems ) {
            items.add(new NavigationItem(string, icons[++i]));
        }

//		// If the user is admin
//		int admin = SessionGlobalData.getInstance().getUser().getPrivilegeLevel();
//		Log.e("", "Admin : "+ admin);
//		if(  admin == 1 ) {
//			items.add(new NavigationItem("נהל משתמשים", R.drawable.users));
//		}
    }

    public void startTestClick(View view) {
        // with the chosen templateId i want to pull from the server the requested template
        // by calling the asynctask class.

        final MySQLiteHelper mySql = application.getMySQLhelper();
        if(mySql.isDBfull()) {
            String name = SessionGlobalData.getInstance().getTest().getInstitutionName();
            new AlertDialog.Builder(this)
                    .setTitle("כבר שמורים 10 מבדקים!")
                    .setMessage("מחק/שלח מבדק/ים כדי לפנות מקום...")
                    .setPositiveButton("למבדקים השמורים", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("בטל", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // stopping the new test creation
                            return;
                        }
                    })
                    .setIcon(R.drawable.warning)
                    .show();
        }
        else {
            instiTitle = institutionEdit.getText().toString();

            if( instiTitle == null || instiTitle.equals("") ) {
                Toast.makeText(NewTestActivity.this, getResources().getString(R.string.insti_name_error_msg), Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the template is already in the local db
            // if not - connect to server and get the template data from the db in the server
            if( !(mySql.isTemplateInDB(typesSpinner.getSelectedItem().toString()))) {
                ConnectionAsyncTask createTemplateTask = new ConnectionAsyncTask(NewTestActivity.this, ConnectionAsyncTask.CREATE_TEMPLATE_TASK,
                        NewTestActivity.this);
                createTemplateTask.execute();
            } else { // if it is in the local db, get it from there
                Template template = mySql.getTemplate(typesSpinner.getSelectedItem().toString());

                SessionGlobalData.getInstance().setTestTemplate(template);
                sessionGlobalData.setInstitutionTilte(instiTitle);
                sessionGlobalData.setTestDate(dateEdit.getText().toString());
                // initialize the test object of this session
                SessionGlobalData.getInstance().initTest();


                // satrt the test activity
                Intent startTestIntent = new Intent(this, TestActivity2.class);
                startActivity(startTestIntent);
            }
        }
    }



//	public static MySQLiteHelper getMySQLHelper() {
//		return mySQLHelper;
//	}

    /**
     * The NavigationItemAdapter inner class adapts between the NavigationItem list to
     * the list view.
     * The NavigationItem 'text' and 'icon' set to the custom layout's views values
     * @author Shira Elitzur
     *
     */
    private class NavigationItemAdapter extends ArrayAdapter<NavigationItem> {

        private List<NavigationItem> items;
        private int layout;

        public NavigationItemAdapter(Context context, int layout, List<NavigationItem> items ) {
            super(context, layout, items);
            this.layout = layout;
            this.items = items;
        }

        @Override
        public View getView(int position, View row, ViewGroup parent) {
            NavigationItem item = items.get(position);

            if ( row == null ) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(layout, parent, false);
            }

            TextView itemTitle = (TextView)row.findViewById(R.id.itemTitle);
            itemTitle.setTextSize(/*getResources().getDimension(R.dimen.itemTitleSize)*/16);
            itemTitle.setText(item.getText());
            ImageView icon = (ImageView)row.findViewById(R.id.itemIcon);
            icon.setBackgroundResource(item.getIcon());


            return row;
        }
    }

    private class NavItemListener implements OnItemClickListener, TaskCycle {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long arg3) {

            if( position == 1 ) {

            }

            else if( position == 2 ) {
                Intent listIntent = new Intent(NewTestActivity.this, TestsListActivity.class);
                startActivity(listIntent);

                drawerListView.setItemChecked(1, true);
            } else if( position == 3 ) {
                Intent aboutIntent = new Intent(NewTestActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                finish();
                drawerListView.setItemChecked(position, true);

            } //else if( position == 4 ) { // Logout
//
//				new AlertDialog.Builder(NewTestActivity.this)
//			    .setTitle(getResources().getString(R.string.logout_title))
//			    .setMessage(getResources().getString(R.string.logout_message))
//			    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//			        public void onClick(DialogInterface dialog, int which) {
//
//			        	Intent loginIntent = new Intent(NewTestActivity.this, LoginActivity.class);
//
//						SharedPreferences.Editor editor = sharedPrefs.edit();
//						application.setSharedPrefs_userName(null);
//						//editor.putString(application.USER_NAME(), null);
//
//						editor.putString(application.PASSWORD(), null);
//
//						startActivity(loginIntent);
//			        }
//			     })
//			    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//			        public void onClick(DialogInterface dialog, int which) {
//			        	drawerListView.setItemChecked(1, true);
//			        }
//			     })
//			    .setIcon(R.drawable.warning)
//			     .show();
//
//			} else if ( position == 5 ) {// managing users
//				Intent usersIntent = new Intent(NewTestActivity.this, UsersManagementActivity.class);
//				startActivity(usersIntent);
//			}


            mDrawerLayout.closeDrawer(drawerListView);

        }

        @Override
        public void onTaskCompleted(boolean result, Object ob, int taskId ) {
            switch( taskId ) {
                case OPEN_SAVED_TESTS_TASK:
                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                    break;
            }

        }

        @Override
        public void onProgress(int taskId, int prog) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean task( int taskId ) {

            switch( taskId ) {
                case OPEN_SAVED_TESTS_TASK:


                    break;
            }

            return true;

        }

        @Override
        public void beforeTask() {

            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * This method set to the specific view and its sub views if its a container ,
     * touch listeners that hides the keyboard.
     * @param view
     */
    public void setTouchListener(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    UIUtils.hideSoftKeyboard(NewTestActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setTouchListener(innerView);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void beforeTask() {
        start.setEnabled(false);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        dateEdit.setEnabled(false);
        institutionEdit.setEnabled(false);
        typesSpinner.setEnabled(false);
        drawerListView.setEnabled(false);


    }

    @Override
    public void onTaskCompleted(boolean result, Object ob, int testId ) {



        if ( result ) {

            sessionGlobalData.getTest().getTemplate().getInstitutionDetails().get(0).setEditText(instiTitle);

            application.getMySQLhelper().insertTemplateData(sessionGlobalData.getTest().getTemplate());

            Intent startTestIntent = new Intent(this, TestActivity2.class);
            startActivity(startTestIntent);


        } else {
            this.onResume();
            Toast.makeText(this, getResources().getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onProgress(int taskId, int prog) {
        Toast.makeText(this, "מייבא נתוני מבדק מהשרת...", Toast.LENGTH_SHORT).show();

    }

    // called from onBackground!
    @Override
    public boolean task( int taskId ) {


        templateType = typesSpinner.getSelectedItem().toString();
        int templateId;
//		instiTitle = institutionEdit.getText().toString();
//
//		if( instiTitle == null || instiTitle.equals("") ) {
//			Toast.makeText(NewTestActivity.this, getResources().getString(R.string.insti_name_error_msg), Toast.LENGTH_SHORT).show();
//			return false;
//		}

        if ( templateType.equals("בית ספר") || templateType.equals("School") ) {
            templateId = 1;
        } else if (templateType.equals("גן ילדים") || templateType.equals("Kindergarten") ){
            templateId = 2;
        } else if (templateType.equals("משפחתון") || templateType.equals("Mishpachton") ){
            templateId = 3;
        } else {
            templateId = 0;
        }

        //application.setInstitutionTilte(institutionEdit.getText().toString());
        //mySQLHelper.addTest(templateID, institutionName, date);

        // Assign the template id, insti name and test's date to the sessionGlobalData obj.
        sessionGlobalData.setTemplateID(templateId);
        sessionGlobalData.setInstitutionTilte(instiTitle);
        sessionGlobalData.setTestDate(dateEdit.getText().toString());

        return true;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateEdit.setText( day+"/"+(month+1)+"/"+year);
        }
    }

}
