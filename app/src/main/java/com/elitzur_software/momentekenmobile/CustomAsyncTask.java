package com.elitzur_software.momentekenmobile;

import android.content.Context;
import android.os.AsyncTask;

public class CustomAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	
	TaskCycle context;
	int taskId;
	public static final int SAVE_IMAGE_T = 5678;
	public static final int PULL_IMAGE_T = 5679;
	
	public CustomAsyncTask( TaskCycle context, int taskId ) {
		this.context = context;
		this.taskId = taskId;
	}
	
	@Override
	protected void onPreExecute() {
		context.beforeTask();
		super.onPreExecute();
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		return context.task( taskId );
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		context.onTaskCompleted(result, null, taskId);
		super.onPostExecute(result);
		
		
	}

}
