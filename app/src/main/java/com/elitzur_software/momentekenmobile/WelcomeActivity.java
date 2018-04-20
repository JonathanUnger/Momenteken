package com.elitzur_software.momentekenmobile;

import java.net.*;
import java.io.*;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elitzur_software.momentekenmobile.ConnectionAsyncTask;
import com.elitzur_software.momentekenmobile.NewTestActivity;
import com.elitzur_software.momentekenmobile.R;
import com.elitzur_software.momentekenmobile.TaskCycle;

public class WelcomeActivity extends Activity implements TaskCycle {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // This task shows the progress cycle
        new ConnectionAsyncTask(this, ConnectionAsyncTask.LOGIN_TASK, this).execute();
    }

// Temporarily commented out!! 19.04.18, as a first step in migration from eclipse
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTaskCompleted( boolean result , Object ob, int taskId ) {
//		if(result) {
//			Intent loginIntent = new Intent(this,LoginActivity.class);
//			startActivity(loginIntent);
//
//		} else {
//			Toast connectErrorToast = Toast.makeText(this, getResources().getString(R.string.connect_failure), Toast.LENGTH_LONG);
//			connectErrorToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//			connectErrorToast.show();
//
//		}

        Intent newTest = new Intent(this,NewTestActivity.class);
        startActivity(newTest);

        finish();
    }

    @Override
    public void finish() {
        super.finish();
        // makes this activity to fade out
        overridePendingTransition(R.anim.appear,R.anim.disappear);
    }

    @Override
    public void onProgress(int taskId,int prog) {

    }

    @Override
    public boolean task( int taskId ) {
        return true;

    }


    @Override
    public void beforeTask() {
        // TODO Auto-generated method stub

    }
}
