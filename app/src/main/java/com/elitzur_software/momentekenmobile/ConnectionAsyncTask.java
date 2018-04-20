package com.elitzur_software.momentekenmobile;

import java.io.*;
import java.net.*;

import data.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ConnectionAsyncTask extends AsyncTask<Void,Integer,Boolean> {
	
	private Socket socket;
	private OutputStream output;
	private ObjectOutputStream oos;
	private InputStream input;
	private ObjectInputStream ois;
	private Context context;
	private int task;
	private TaskCycle taskCycle;
	private Template template;
	private boolean connected = false;
	
	public final static String SERVER_ADDRESS =  "68.169.55.80";
	//public final static String SERVER_ADDRESS =  "10.0.0.141";
	public final static int PORT = 8993;
	public final static int LOGIN_TASK = 1;
	public final static int CREATE_TEMPLATE_TASK = 2;
	public final static int SEND_TEST = 3;
	public final static int CHECK_USER = 4;
	public final static int USERS_LIST = 7;
	public final static int ADD_USER = 77;
	public final static int UPDATE_USER = 777;
	public final static int DELETE_USER = 7777;
	
	private boolean result = false;
	
	
	public ConnectionAsyncTask( Context context, int task, TaskCycle otc ) {
		this.context = context;
		this.task = task;
		this.taskCycle = otc;
	}
	
	@Override
	protected void onPreExecute() {
		taskCycle.beforeTask();
		super.onPreExecute();
	}
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		
		boolean result = false;
		
		switch (task) 
		{
		case LOGIN_TASK :  firstLogin();
			break;
		case CREATE_TEMPLATE_TASK : 
			publishProgress(1);
			taskCycle.task(CREATE_TEMPLATE_TASK);
			result = createTemplate();
									
			break;
		case SEND_TEST : result =  sendTest();
			break;
		case CHECK_USER : result = checkUser();
			break;
		case USERS_LIST : result = getUsersList();
			break;
		case ADD_USER : result =addUser();
			break;
		case UPDATE_USER : result =updateUser();
			break;
		case DELETE_USER : result =deleteUser();
		}
		
		return result;
		
	}
	
	
	private boolean createTemplate() {
		
		if( connectToServer() ) {
				
				Data request;
				Data response;
				
				Log.d("","inside create template task");
				request = new Data( Data.TEMPLATE_REQUEST_DT );
				request.setTemplateID(SessionGlobalData.getInstance().getTemplateID());
				try {
		
					
					oos.writeObject(request);
					response = (Data)ois.readObject();
					template = response.getTemplate();
					
					SessionGlobalData.getInstance().setTestTemplate(template);
					
					Log.d("",SessionGlobalData.getInstance().getTestTemplate().getTitle());
		
					// initialize the test object of this session
					SessionGlobalData.getInstance().initTest();
					
				} catch (IOException e) {
					Log.e("debug","IOException");
					e.printStackTrace();
					return false;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					Log.e("debug","ClassNotFoundException");
					e.printStackTrace();
					return false;
				}
				
				closeConnection();
		
				return true;
		
		} else {
			return false;
		}
		
		

	}

	private void closeConnection() {
		try {
			oos.writeObject(new Data(Data.CLOSE_SOCKET));

			socket.close();
			
			output.close();
			input.close();
			ois.close();
			oos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean connectToServer() {
		try {
			socket = new Socket();
			SocketAddress remoteAddr = new InetSocketAddress(SERVER_ADDRESS, PORT);
			socket.connect(remoteAddr, 10000);
			output = socket.getOutputStream();
			oos = new ObjectOutputStream(output);
			input = socket.getInputStream();
			ois = new ObjectInputStream(input);
			
			connected = true;
			return true;
			
		} catch (UnknownHostException e) {
			Log.e("debug", "UnknownHostException");
			return false;
		} catch (IOException e) {
			Log.e("debug", "connect to server - IOException");
			return false;
		}
	}
	
	public boolean addUser() {
		
		if ( !connectToServer() ) {
			return false;
		}
		
		
		Data request = new Data(Data.INSERT_USER_REQUEST_DT);
		request.setUser(SessionGlobalData.getInstance().getUserToDo());
		
		try {
			oos.writeObject(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		closeConnection();
		return true;
	}
	
	public boolean updateUser() {
		
		if ( !connectToServer() ) {
			return false;
		}
		
		
		Data request = new Data(Data.UPDATE_USER_REQUEST_DT);
		request.setUser(SessionGlobalData.getInstance().getUserToDo());
		
		try {
			oos.writeObject(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		closeConnection();
		return true;
	}
	
	
	public boolean deleteUser() {
		
		if ( !connectToServer() ) {
			return false;
		}
		
		
		Data request = new Data(Data.DELETE_USER_REQUEST_DT);
		request.setUser(SessionGlobalData.getInstance().getUserToDo());
		
		try {
			oos.writeObject(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		closeConnection();
		return true;
	}
	
	/**
	 * The method connecting to the server to make sure that connection is available.
	 * @return
	 */
	public void firstLogin() {
		
		//boolean isConnectionValid = connectToServer();

		// for displaying the splash screen for couple of seconds
		for(int i=1;i<=5;i++)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		if( connected ) {
//			closeConnection();
//		}
//		//return isConnectionValid;	
	}
	
	private boolean sendTest() {
		// getApplication() is a method of Activity class. so in order to use
		// the application object in a class which is not an activity we must supply an activity
		// instance. and here i already have the context that passed to the constructor which
		// is an activity so i use it.
		Activity activity = (Activity)context;
		MomentekenApp app = (MomentekenApp)activity.getApplication();
		int progressCounter = 0;
		MySQLiteHelper mySql = app.getMySQLhelper();
		int testId;
		
		if (!connectToServer()) {
			return false;
		}
		
		Data request;
		Data response;
		
		// storing the test from the testInfo in a local variable for easier use
		Test origTest = SessionGlobalData.getInstance().getTest();
		
		// creating a new test object that will contain the test data without the
		// chapters and items data. template, name and date
		Test testToSend = new Test( origTest.getTemplate(), 
				origTest.getInstitutionName(), origTest.getDate() );
		testToSend.setUser(origTest.getUser());

		
		request = new Data( Data.INSERT_TEST_DT );
		request.setTest(testToSend);
		try {
			oos.writeObject(request);
			
//			response = (Data)ois.readObject();
//			// i am using the isUser boolean just because i dont want to change the Data
//			// class now, in the three places where it is (one of them is the compiled server)
//			// But i am using this boolean to indicate if the test was successfully written in the db 
//			result = response.isUser();
			
			response = (Data)ois.readObject();
			
			//closeConnection();
			
			testId = response.getTestId();
			
			TestChapter chapterToSend;
			TestItem itemToSend;
			int chapterId;
			int chaptersAmount = origTest.getChapters().size();
			for( TestChapter chapter : origTest.getChapters() ) {
				
				chapterToSend = new TestChapter();
				chapterToSend.setTemplate(chapter.getTemplate());
				
				//connectToServer();
				
				request = new Data(Data.INSERT_CHAPTER_DT);
				request.setObjecto(chapterToSend);
				request.setTestId(testId);
				oos.writeObject(request);
				
				response = (Data)ois.readObject();
				//closeConnection();
				chapterId = response.getChapterId();
				
				for( TestItem item : chapter.getItems() ) {
					
					if( item.getImage1Code() != null ) {
						item.setImage1(mySql.selectImage(item.getImage1Code()));
					}
					
					if( item.getImage2Code() != null ) {
						item.setImage2(mySql.selectImage(item.getImage2Code()));
					}
					
					
					
					//connectToServer();
					request = new Data(Data.INSERT_ITEM_DT);
					request.setChapterId(chapterId);
					request.setTestId(testId);
					request.setObjecto(item);
					
					oos.writeObject(request);
					
					//closeConnection();
					item.setImage1(null);
					item.setImage2(null);
					
					mySql.deleteImage(item.getImage1Code());
					mySql.deleteImage(item.getImage2Code());
				}
				taskCycle.onProgress(task, progressCounter+=(100/chaptersAmount));
			}
			
			// after the sending of the whole test i send request to close the database connection 
			// which the server opened when the first request of the test was sent.
			// (because the server can't know which item is the last one).
			//connectToServer();

			request = new Data(Data.CLOSE_SQL_CONNECTION);
			request.setTestId(testId);
			oos.writeObject(request);
			taskCycle.onProgress(task, 100);
			//closeConnection();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
		
		return true;
		
	}
	
	
	private boolean checkUser() {
		
		if ( !connectToServer() ) {
			return false;
		}
		
		
		Data request;
		Data response;
		
		request = new Data( Data.LOGIN_REQUEST_DT );
		// pull the user data to check from the testInfo global object
		request.setUser(SessionGlobalData.getInstance().getUser());
		
		try {
			oos.writeObject(request);
			response = (Data)ois.readObject();
			
			// get the user id from the database in the server from the Data object
			taskCycle.task( response.getUserId());
			if( response.isUser()) {
				SessionGlobalData.getInstance().setUser(response.getUser());
				Log.e("", "User Admin - " + response.getUser().getPrivilegeLevel() );
			}
			return response.isUser();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		closeConnection();
		
		return false;
	}
	
	private boolean getUsersList() {
		Data request;
		Data response;
		if ( !connectToServer() ) {
			return false;
		}
		
		request = new Data(Data.USERS_RESULT_SET_REQUEST_DT);
		
		try {
			oos.writeObject(request);
			response = (Data)ois.readObject();
			
			SessionGlobalData.getInstance().setUsersList(
					response.getArrayList());
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		
		closeConnection();
		return true;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		
		taskCycle.onTaskCompleted(result, template , task);
	}

}
