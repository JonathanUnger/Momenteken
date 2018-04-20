package com.elitzur_software.momentekenmobile;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import com.elitzur_software.momentekenmobile.R;

import data.Test;
import data.TestChapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This activity is the test activity. It has ListView with the test's chapters when
 * click on one chapter leads to this chapter activity
 *
 * @author Shira Elitzur
 *
 */
public class TestActivity2 extends ActionBarActivity implements TaskCycle {

    /**
     * A ListView to display the chapters of the test, for the
     * chapters list view from the layout file.
     */
    private ListView chaptersListView;
    /**
     *
     */
    private ActionBarDrawerToggle toggle;


    private String headerTitle;
    /**
     * The application's global object
     */
    private MomentekenApp application;

    private ProgressBar progressBar;
    public static final int SAVE_TEST_TASK = 333;
    public static final int DELETE_IMGS_TASK = 1234;
    public static final int DELETE_TEST_TASK = 444;
    public static final int VERIFY_TEST_TASK = 5555;

    private boolean thinking = false;
    private boolean first = true;
    private Test testToDel;
    private Test testForTask;
    ArrayList<Test> tests;
    private String name;
    ProgressDialog progress;
    private TestChapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the customized layout for this activity
        setContentView(R.layout.activity_test2);
        application = (MomentekenApp)getApplication();
        // Find the graphical components from the layout xml file and
        // assign it to the matching variables.
        getComponents();
        // Fill the components with data
        initComponents();
        name = null; // for now
        headerTitle = SessionGlobalData.getInstance().getTest().getInstitutionName();
        setTitle("   "+headerTitle+" ");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.drawer_ic));
    }

//	private void initActionBarAndDrawer() {
//
//		// sets the activity title to the institution's name
//		// for the title of the action bar in the activity
//
//		// a nice trick for customizing the action bar title's color
//        // without finding the title's view id:
//        // String title = " "+getResources().getString(R.string.test_drawer_title)+" ";
//        // setTitle((Html.fromHtml("<font color=\"#FF4444\">" + title + "</font>")));
//
//        // i used spaces instead of left padding (that didn't work) between the title
//        // and the icon. Not so elegant... but it works :)
//		setTitle(" "+headerTitle+" ");
//
//		/* The Navigation Drawer Setting:
//		  	In the test activity the navigation display a list
//		  	of operations for this specific test
//		 */
//
//		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//		items = new ArrayList<NavigationItem>();
//		initNavigationItemsList();
//
//		drawerListView = (ListView)findViewById(R.id.left_drawer);
//		// the drawer's header definition:
//		// first set ad header
//		ViewGroup mTop = (ViewGroup)getLayoutInflater().inflate(R.layout.test_drawer_header, drawerListView, false);
//		drawerListView.addHeaderView(mTop, null, false);
//		// then find the views and set the text to the test's details
//		drawerHeaderTitle = (TextView)mTop.findViewById(R.id.headerTitle);
//
//		drawerHeaderTitle.setText(name);
//
//		drawerHeaderDate = (TextView)mTop.findViewById(R.id.dateHeaderTextView);
//		drawerHeaderDate.setText(SessionGlobalData.getInstance().getTestDate());
//
//
//		NavigationItemAdapter adapter = new NavigationItemAdapter(this,
//				R.layout.navigation_drawer_item,
//				items);
//		drawerListView.setAdapter(adapter);
//
//		drawerListView.setOnItemClickListener( new NavItemListener());
//
//
//
//		toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.drawer_ic,
//                R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//
//            	// a nice trick for customizing the action bar title's color
//                // without finding the title's view id:
//                // String title = " "+getResources().getString(R.string.test_drawer_title)+" ";
//                // setTitle((Html.fromHtml("<font color=\"#FF4444\">" + title + "</font>")));
//
//                // i used spaces instead of left padding (that didn't work) between the title
//                // and the icon. Not so elegant... but it works :)
//
//
//                setTitle(headerTitle+" ");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                // a nice trick for customizing the action bar title's color
//                // without finding the title's view id:
//                // String title = " "+getResources().getString(R.string.test_drawer_title)+" ";
//                // setTitle((Html.fromHtml("<font color=\"#FF4444\">" + title + "</font>")));
//
//                // i used spaces instead of left padding (that didn't work) between the title
//                // and the icon. Not so elegant... but it works :)
//                setTitle(" "+getResources().getString(R.string.test_drawer_title)+" ");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        // Set the drawer toggle as the DrawerListener
//        mDrawerLayout.setDrawerListener(toggle);
//		// These two lines set the app icon in the action bar
//		//        getSupportActionBar().setHomeButtonEnabled(true);
//		//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        // setting the up button, mine is customized and open the navigation drawer
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.drawer_ic));
//        //getSupportActionBar().setIcon(R.drawable.drawer_ic);
//        toggle.setDrawerIndicatorEnabled(true);
//	}


    @Override
    protected void onPause() {
        //Toast.makeText(this, "onPause!", Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(this, "onStop!", Toast.LENGTH_LONG).show();
        super.onStop();
    }


    @Override
    protected void onResume() {
        // auto save of the test on any onResume but not in the first time
        if(!first) {
            SessionGlobalData.getInstance().getTest().setSaved(true);
            CustomAsyncTask task = new CustomAsyncTask(TestActivity2.this, SAVE_TEST_TASK);
            task.execute();
        }
        first = false;

        initComponents(); // i added it because i wanted the list to refresh and color the filled chapters
        // in green. invalidate or set visibility didn't make that happen
        super.onResume();
        Log.d("","Indise ON RESUME!!!");
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

    /**
     * I override the back behavior to pop a dialog of saving the test before leaving the activity
     */
    @SuppressLint("InlinedApi")
    @Override
    public void onBackPressed() {

        if ( thinking ) {
            return;
        }
        // here i checked if the test is already saved and if it is i am not showing
        // the dialog that ask if the app should save or not
        // i commented it because after we added the auto save we want to ask even for saved test
        // if we should keep it or not
//
//		if( SessionGlobalData.getInstance().getTest().isSaved()) {
//			TestActivity2.super.onBackPressed();
//			return;
//		}
        // here i wanted to use custom theme to the alert dialog
        // alert dialog builder has constructor that receives theme
        // when i used my custom theme (R.style.AlertDialogCustom)
        // the dialog appears fine but throws nullPointerException on dismiss
        // if i will have extra time i will fix that
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.save_test_title))
                .setMessage(getResources().getString(R.string.save_test_msg))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SessionGlobalData.getInstance().getTest().setSaved(true);
                        CustomAsyncTask task = new CustomAsyncTask(TestActivity2.this, SAVE_TEST_TASK);
                        task.execute();
                        TestActivity2.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The situation here is that the user chose to leave the test activity before sending the test
                        // and without saving it. Means that he decided that this test is no longer needed.
                        // So i want to delete the images from the local db for two reasons:
                        // 1. This information has no use
                        // 2. Probably the user will start a new test with the same institution and date and these two
                        // combine the unique string that recognize the image in the local db
                        CustomAsyncTask deleteImgsTask = new CustomAsyncTask(TestActivity2.this, DELETE_IMGS_TASK);
                        testToDel = SessionGlobalData.getInstance().getTest();
                        deleteImgsTask.execute();
                        CustomAsyncTask deleteTestTask = new CustomAsyncTask(TestActivity2.this, DELETE_TEST_TASK);
                        testForTask = SessionGlobalData.getInstance().getTest();
                        deleteTestTask.execute();
                        TestActivity2.super.onBackPressed();
                    }
                })
                .setIcon(R.drawable.warning)
                .show();

    }


    /**
     * Find the graphical components from the layout xml file and
     * assign it to the matching variables.
     */
    private void getComponents() {
        chaptersListView = (ListView)findViewById(R.id.chaptersListView2);
        progressBar = (ProgressBar)findViewById(R.id.progress);
    }

    private void initComponents() {

        ChaptersListAdapter listAdapter = new ChaptersListAdapter(
                this, R.layout.single_chapter_item_layout,
                SessionGlobalData.getInstance().getTest().getChapters());

        chaptersListView.setAdapter(listAdapter);
        chaptersListView.setOnItemClickListener(new ItemClickListener());
    }

//	private void initNavigationItemsList() {
//		String[] stringItems = getResources().getStringArray(R.array.testOperations);
//		int[] icons = { /*R.drawable.save,*/
//						R.drawable.send/*,
//						R.drawable.institution_details*/};
//		int i = -1;
//		for( String string : stringItems ) {
//			items.add(new NavigationItem(string, icons[++i]));
//		}
//	}

    /**
     * The adapter class that adapts between the list of chapters to the graphical list view
     * @author Shira Elitzur
     *
     */
    private class ChaptersListAdapter extends ArrayAdapter<TestChapter> {

        List<TestChapter> chapters;
        int layout;
        ViewHolder holder;

        public ChaptersListAdapter(Context context, int layout, List<TestChapter> chapters ) {
            super(context, layout, chapters);
            this.layout = layout;
            this.chapters = chapters;


        }

        @Override
        public View getView(int position, View row, ViewGroup parent) {
            TestChapter chapter = chapters.get(position);


            if ( row == null ) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(layout, parent, false);

                holder = new ViewHolder();
                holder.title = (TextView)row.findViewById(R.id.chapterTitle);
                row.setTag(holder);
            }else {
                holder = (ViewHolder)row.getTag();
            }

            holder.title.setText(chapter.getTemplate().getTitle());
            if( chapter.isValid() ) {
                holder.title.setTextColor(getResources().getColor(R.color.ok_text_color));
            } else {
                holder.title.setTextColor(getResources().getColor(android.R.color.white));
            }
            return row;
        }

    }

    private class ViewHolder {
        TextView title;
    }

    /**
     *
     * This inner class describes what happens when the user click on one of the chapters
     * in the list of chapters.
     * @author Shira Elitzur
     *
     */
    private class ItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long arg3) {
            SessionGlobalData.getInstance().getTest().setSaved(false);

            chapter = SessionGlobalData.getInstance().getTest().getChapters().get(position);
            if( chapter.getTemplate().getTitle().equalsIgnoreCase("פרטי המוסד")) {
                Intent detailsIntent = new Intent( TestActivity2.this, InstitutionDetailsActivity.class);
                detailsIntent.putExtra("chapterPosition", position);
                startActivity(detailsIntent);
            } else {
                Intent chapterIntent = new Intent( TestActivity2.this, ChapterActivity2.class);
                chapterIntent.putExtra("chapterPosition", position);
                startActivity(chapterIntent);
            }

        }

    }

//	/**
//	 * The NavigationItemAdapter inner class adapts between the NavigationItem list to
//	 * the list view.
//	 * The NavigationItem 'text' and 'icon' set to the custom layout's views values
//	 * @author Shira Elitzur
//	 *
//	 */
//	private class NavigationItemAdapter extends ArrayAdapter<NavigationItem> {
//
//		private List<NavigationItem> items;
//		private int layout;
//
//		public NavigationItemAdapter(Context context, int layout, List<NavigationItem> items ) {
//			super(context, layout, items);
//			this.layout = layout;
//			this.items = items;
//		}
//
//		@Override
//		public View getView(int position, View row, ViewGroup parent) {
//			NavigationItem item = items.get(position);
//
//			if ( row == null ) {
//				LayoutInflater inflater = getLayoutInflater();
//				row = inflater.inflate(layout, parent, false);
//			}
//
//			TextView itemTitle = (TextView)row.findViewById(R.id.itemTitle);
//			itemTitle.setText(item.getText());
//			ImageView icon = (ImageView)row.findViewById(R.id.itemIcon);
//			icon.setBackgroundResource(item.getIcon());
//
//
//			return row;
//		}
//	}

    public void onSendPressed( View view ) {
        new AlertDialog.Builder(TestActivity2.this)
                .setTitle("אתה בטוח שברצונך לשלוח את המבדק?")
                .setMessage("לאחר שליחת המבדק לא ניתן לערוך בו שינויים.\nלעומת זאת כעת אתה יכול לשמור את המבדק ולשלוח\nאותו רק כשיהיה מוכן...")
                .setPositiveButton("שלח", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Test test = SessionGlobalData.getInstance().getTest();
                        if ( test != null /*&& test.isValid()*/) {
                            testForTask = SessionGlobalData.getInstance().getTest();
                            int currentOrientation = getResources().getConfiguration().orientation;
                            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                                TestActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                            }
                            else {
                                TestActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                            }

                            TestActivity2.this.
                                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                            thinking = true;
                            progress = new ProgressDialog(TestActivity2.this);
                            progress.setMessage(getResources().getString(R.string.test_progress_dialog_title));
                            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progress.setCanceledOnTouchOutside(false);
                            progress.setCancelable(false);
                            progress.show();
                            ConnectionAsyncTask sendTestAsyncTask = new ConnectionAsyncTask(TestActivity2.this,
                                    ConnectionAsyncTask.SEND_TEST, TestActivity2.this);
                            sendTestAsyncTask.execute();

                        } else {

                            Toast.makeText(TestActivity2.this,
                                    getResources().getString(R.string.send_test_error_msg), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("אל תשלח", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.warning)
                .show();
    }

//	private class NavItemListener implements OnItemClickListener {
//
//		@Override
//		public void onItemClick(AdapterView<?> adapter, View view, int position,
//				long arg3) {
//			Log.e("","position: "+position);
////			// save test
////			if( position == 1 ) {
////				SessionGlobalData.getInstance().getTest().setSaved(true);
////				CustomAsyncTask task = new CustomAsyncTask(TestActivity2.this, SAVE_TEST_TASK);
////				task.execute();
////			}
//			// send test
//			if( position == 1 ) {
//
//				new AlertDialog.Builder(TestActivity2.this)
//			    .setTitle("אתה בטוח שברצונך לשלוח את המבדק?")
//			    .setMessage("לאחר שליחת המבדק לא ניתן לערוך בו שינויים.\nלעומת זאת כעת אתה יכול לשמור את המבדק ולשלוח\nאותו רק כשיהיה מוכן...")
//			    .setPositiveButton("שלח", new DialogInterface.OnClickListener() {
//			        public void onClick(DialogInterface dialog, int which) {
//
//						Test test = SessionGlobalData.getInstance().getTest();
//						if ( test != null /*&& test.isValid()*/) {
//							testForTask = SessionGlobalData.getInstance().getTest();
//							int currentOrientation = getResources().getConfiguration().orientation;
//							if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//								TestActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//							}
//							else {
//								TestActivity2.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//							}
//
//							TestActivity2.this.
//							progressBar.setVisibility(ProgressBar.INVISIBLE);
//							thinking = true;
//							progress = new ProgressDialog(TestActivity2.this);
//							progress.setMessage(getResources().getString(R.string.test_progress_dialog_title));
//							progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//							progress.setCanceledOnTouchOutside(false);
//							progress.setCancelable(false);
//							progress.show();
//							ConnectionAsyncTask sendTestAsyncTask = new ConnectionAsyncTask(TestActivity2.this,
//									ConnectionAsyncTask.SEND_TEST, TestActivity2.this);
//							sendTestAsyncTask.execute();
//
//						} else {
//
//							Toast.makeText(TestActivity2.this,
//									getResources().getString(R.string.send_test_error_msg), Toast.LENGTH_SHORT).show();
//						}
//			        }
//			     })
//			    .setNegativeButton("אל תשלח", new DialogInterface.OnClickListener() {
//			        public void onClick(DialogInterface dialog, int which) {
//
//			        }
//			     })
//			    .setIcon(R.drawable.warning)
//			     .show();
//
//			} else if( position == 3 ) {
//				Intent institutionDetailsIntent = new Intent(TestActivity2.this, InstitutionDetailsActivity.class);
//				startActivityForResult(institutionDetailsIntent, 2);
//			}
//
//			drawerListView.setItemChecked(0, true);
//		    mDrawerLayout.closeDrawer(drawerListView);
//
//		}
//
//

//	}

    @Override
    public void onTaskCompleted(boolean result, Object ob, int taskId ) {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        chaptersListView.setEnabled(true);
        thinking = false;

        switch(taskId) {
            case SAVE_TEST_TASK :
                if ( result ) {
                    Toast.makeText(TestActivity2.this,
                            "נשמר!", Toast.LENGTH_SHORT).show();
                }
                break;
            case ConnectionAsyncTask.SEND_TEST :

                if( result ){
                    progress.dismiss();
                    Toast.makeText(TestActivity2.this,
                            getResources().getString(R.string.send_test_msg), Toast.LENGTH_LONG).show();
                    finish();
                    CustomAsyncTask task = new CustomAsyncTask(TestActivity2.this, DELETE_TEST_TASK);
                    task.execute();

                } else {
                    progress.dismiss();
                    Toast.makeText(TestActivity2.this,
                            getResources().getString(R.string.send_test_connection_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onProgress( int taskId, int prog ) {
        // TODO Auto-generated method stub

        if (taskId == ConnectionAsyncTask.SEND_TEST ) {
            progress.setProgress(prog);
        }

    }

    @Override
    public boolean task( int taskId ) {
        MySQLiteHelper mySql = application.getMySQLhelper();
        boolean result;
        switch(taskId) {

            case SAVE_TEST_TASK :
                Log.e("","save press");
                result = mySql.addTest(SessionGlobalData.getInstance().getTest());
                return result;

            case DELETE_TEST_TASK:
                // because this method invokes in a separate thread when the activity is not displayed anymore
                // maybe the application has a new test in the testInfo instance. So i kept the correct
                // one and i am deleting it.
                mySql.deleteTest(testForTask , -1);
                break;
            // This task deletes the images of a test that was not completed.
            // The purpose is to clean the sqlite database from the extra information
            case DELETE_IMGS_TASK:
                mySql.deleteTestImgs(testToDel);
                break;
        }

        return true;


    }

    @Override
    public void beforeTask() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        chaptersListView.setEnabled(false);
        thinking = true;
        //drawerListView.setEnabled(false);


    }
}
