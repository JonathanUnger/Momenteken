package com.elitzur_software.momentekenmobile;

import java.util.ArrayList;

import data.InstitutionDetail;
import data.Template;
import data.TemplateChapter;
import data.TemplateItem;
import data.Test;
import data.TestChapter;
import data.TestItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "momentekenSQLiteDB";
	
	private static final String IMAGES_TABLE_NAME = "images";
	private static final String IMAGE_ID = "ImageId";
	private static final String IMAGE_UNIQE_CODE = "ImageUniqeCode";
	private static final String IMAGE = "Image";

	private static final String IMAGES_TABLE_CREATE =
    		"CREATE TABLE " + IMAGES_TABLE_NAME + " (" +
    				IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
    				+ IMAGE_UNIQE_CODE + " TEXT, " 
    				+ IMAGE +" BLOB );"; 	
	
	private static final String TESTS_TABLE_NAME = "tests";
	private static final String TEST_ID = "TestId";
	private static final String INSTITUTION_NAME = "InstitutionName";
	private static final String TEST_DATE = "Date";
	private static final String TEMPLATE_ID = "TemplateId";
	
    private static final String TESTS_TABLE_CREATE =
    		"CREATE TABLE " + TESTS_TABLE_NAME + " (" +
    				TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INSTITUTION_NAME + " TEXT, " 
    				+ TEST_DATE +" TEXT, " +  
    				TEMPLATE_ID + " INTEGER );"; 
	
	private static final String CHAPTERS_TABLE_NAME = "chapters";
	private static final String CHAPTER_ID = "ChapterId";
	private static final String TEMPLATE_CHAPTER = "TemplateChapter";
	//public static final String TEST_ID = "TestId";
	
    private static final String CHAPTERS_TABLE_CREATE =
    		"CREATE TABLE " + CHAPTERS_TABLE_NAME + " (" +
    				CHAPTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMPLATE_CHAPTER + " INTEGER, " + 
    				TEST_ID + " INTEGER );"; 

	private static final String TEST_ITEMS_TABLE_NAME = "test_items";
	private static final String TEST_ITEM_ID = "TestItemId";
	private static final String APPROVAL = "Approval";
	private static final String FIRST = "First";
	private static final String COMMENT = "Comment";
	private static final String LEVEL = "Level";
	private static final String IMAGE1 = "Image1";
	private static final String IMAGE2 = "Image2";
	private static final String IMAGE1_CODE = "ImageCode1";
	private static final String IMAGE2_CODE = "ImageCode2";
	private static final String TEMPLATE_ITEM_ID = "TemplateItemId";
	//public static final String CHAPTER_ID = "ChapterId";
	
    private static final String TEST_ITEMS_TABLE_CREATE =
    		"CREATE TABLE " + TEST_ITEMS_TABLE_NAME + " (" +
    				TEST_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + APPROVAL + " INTEGER, "
    				+ FIRST + " INTEGER, "
    				+ COMMENT +" TEXT, " + CHAPTER_ID + " INTEGER, " + 
    				TEMPLATE_ITEM_ID + " INTEGER, " + IMAGE1 + " BLOB ," + IMAGE1_CODE + " TEXT, " +
    				IMAGE2 + " BLOB ,"+ IMAGE2_CODE + " TEXT, " + 
    				LEVEL + " INTEGER  );"; 
    
    // The Template's three tables :
    
    // Templates table
	private static final String TEMPLATES_TABLE_NAME = "templates";
	//public static final String TEMPLATE_ID = "TemplateId";
	private static final String TITLE = "Title";
	
    private static final String TEMPLATES_TABLE_CREATE =
    		"CREATE TABLE " + TEMPLATES_TABLE_NAME + " (" +
    				 TITLE +" TEXT, " +  
    				TEMPLATE_ID + " INTEGER );"; 
    
    // Template chapters table
	private static final String TEMPLATE_CHAPTERS_TABLE_NAME = "template_chapters";
	private static final String TEMPLATE_CHAPTER_ID = "TemplateChapterId";
	//public static final String TEMPLATE_ID = "TemplateId";
	//public static final String TITLE = "Title";
	
    private static final String TEMPLATE_CHAPTERS_TABLE_CREATE =
    		"CREATE TABLE " + TEMPLATE_CHAPTERS_TABLE_NAME + " (" +
    				 TITLE +" TEXT, " +  TEMPLATE_CHAPTER_ID + " INTEGER, " +
    				TEMPLATE_ID + " INTEGER );"; 
    
 // Template items table
 	private static final String TEMPLATE_ITEMS_TABLE_NAME = "template_items";
 	//public static final String TEMPLATE_ITEM_ID = "TemplateItemId";
 	private static final String DESCRIPTION = "Description";
 	private static final String AREA = "Area";
 	private static final String ITEM_NUMBER = "ItemNumber";
 	//public static final String TITLE = "Title";
 	
     private static final String TEMPLATE_ITEMS_TABLE_CREATE =
     		"CREATE TABLE " + TEMPLATE_ITEMS_TABLE_NAME + " (" +
     				AREA +" TEXT, " + ITEM_NUMBER +" TEXT, " +
     				 TITLE +" TEXT, " + DESCRIPTION +" TEXT, " + 
     				TEMPLATE_CHAPTER_ID + " INTEGER, " +
     				TEMPLATE_ITEM_ID + " INTEGER );"; 
    
     
//     private static final String INSTITUTIONS_TABLE_NAME = "institutions";
//     private static final String INSTITUTION_ID = "InstitutionId";
//     private static final String ADDRESS = "Address";
//     // private static final String AREA = "";
//     private static final String CITY = "City";
//     private static final String OWNERSHIP = "Ownership";
//     private static final String INSTITUTION_NUMBER = "InatitutionNumber";
//     private static final String NAME = "Name";
//     private static final String PHONE_NUMBER = "PhoneNumber";
//     private static final String NUMBER_OF_STUDENTS = "NumberOfStudents";
//     private static final String PARTICIPANTS_FROM_INSTITUTIONS = "ParticipantsFromInstitution";
//     private static final String PARTICIPANTS_FROM_OWNERSHIP = "ParticipantsFromOwnership";
//     private static final String SUPERVISOR = "Supervisor"; 
//     private static final String FOUNDATION_YEAR = "FoundationYear";
//     private static final String PRINCIPAL_NAME = "PrincipalName";
//     private static final String PRINCIPAL_PHONE = "PrincipalPhone";
//     //private static final String TEST_ID = "";
    
     
//     private static final String INSTITUTIONS_TABLE_CREATE =
//      		"CREATE TABLE " + INSTITUTIONS_TABLE_NAME + " (" +
//      				TEST_ID + " INTEGER, " +
//      				INSTITUTION_ID + " INTEGER, " +
//      				AREA +" TEXT, " +
//      				ADDRESS +" TEXT, " +
//      				CITY +" TEXT, " +
//      				OWNERSHIP +" TEXT, " +
//      				INSTITUTION_NUMBER +" TEXT, " +
//      				INSTITUTION_NAME +" TEXT, " +
//      				PHONE_NUMBER +" TEXT, " +
//      				NUMBER_OF_STUDENTS + " INTEGER, " +
//      				PARTICIPANTS_FROM_INSTITUTIONS +" TEXT, " + 
//      				PARTICIPANTS_FROM_OWNERSHIP +" TEXT, " + 
//      				SUPERVISOR +" TEXT, " + 
//      				PRINCIPAL_NAME +" TEXT, " + 
//      				PRINCIPAL_PHONE +" TEXT, " + 
//      				FOUNDATION_YEAR + " INTEGER );"; 
     
    private static int institutionIndex = 0;
    private static final int DATABASE_VERSION = 1;
    private static final int TESTS_MAX_AMOUNT = 10;
    private SQLiteDatabase myDB;
	
	MySQLiteHelper(Context context) {
		
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	Log.d("sql", "1. sql helper constructor\n");
    	myDB = getWritableDatabase();
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("sql", "2. sql helper onCreate\n");
		db.execSQL(TESTS_TABLE_CREATE);
		db.execSQL(CHAPTERS_TABLE_CREATE);
		db.execSQL(TEST_ITEMS_TABLE_CREATE);

		//db.execSQL(INSTITUTIONS_TABLE_CREATE);

		db.execSQL(TEMPLATE_CHAPTERS_TABLE_CREATE);
		db.execSQL(TEMPLATES_TABLE_CREATE);
		db.execSQL(TEMPLATE_ITEMS_TABLE_CREATE);
		db.execSQL(IMAGES_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer ) {
		Log.d("sql", "3. sql helper onUpgrade\n");
		db.delete(TESTS_TABLE_NAME, null, null);
		db.delete(CHAPTERS_TABLE_NAME, null, null);
		db.delete(TEST_ITEMS_TABLE_NAME, null, null);
		db.delete(TEMPLATE_CHAPTERS_TABLE_NAME, null, null);
		db.delete(TEMPLATES_TABLE_NAME, null, null);
		db.delete(TEMPLATE_ITEMS_TABLE_NAME, null, null);
//		db.delete(INSTITUTIONS_TABLE_NAME, null, null);
		db.delete(IMAGES_TABLE_NAME, null, null);
		
		
		db.execSQL(TESTS_TABLE_CREATE);
		db.execSQL(CHAPTERS_TABLE_CREATE);
		db.execSQL(TEST_ITEMS_TABLE_CREATE);
//		db.execSQL(INSTITUTIONS_TABLE_CREATE);

		db.execSQL(TEMPLATE_CHAPTERS_TABLE_CREATE);
		db.execSQL(TEMPLATES_TABLE_CREATE);
		db.execSQL(TEMPLATE_ITEMS_TABLE_CREATE);

		db.execSQL(IMAGES_TABLE_CREATE);
	}
	
	@Override
	public void onOpen( SQLiteDatabase db) {
		super.onOpen(db);
//		db.execSQL("DELETE FROM " + IMAGES_TABLE_NAME);
		Log.d("sql", "4. sql helper onOpen\n");
	}
	
	// This method insert byte array the represent an image to the images table.
	// The images are of the current test, instead of being kept on the application memory 
	public void insertImage( byte[] imageArray, String code ) {
		
		// Insert new row to the images table (The id is an auto increment column) 
    	ContentValues imageValues = new ContentValues();
    	imageValues.put(IMAGE_UNIQE_CODE, code);
    	imageValues.put(IMAGE, imageArray);
    	myDB.insert(IMAGES_TABLE_NAME, null, imageValues);
    	Log.e("", "finish save : " + code);
	}
	
	/**
	 * The method returns a byte array of the image that match the given code
	 * @param code The code of the requested image
	 * @return
	 */
	public byte[] selectImage( String code ) {
		
		byte[] image = null;
	
		Log.e("", "Image code to pull : " + code);
		
		String[] columns = {IMAGE_UNIQE_CODE, IMAGE};
		String whereCol = IMAGE_UNIQE_CODE + " = ?";
		String[] whereParam = {code};
    	Cursor result = myDB.query(IMAGES_TABLE_NAME, columns, whereCol, whereParam,
    			null, null, null);
    	Log.e("", "result set count = " + result.getCount());
    	if ( result.getCount() == 1 ) {
    		result.moveToFirst();
    		Log.e("", "image code : "+ result.getBlob(result.getColumnIndex(IMAGE)));
    		Log.e("", "image code : "+ result.getString(result.getColumnIndex(IMAGE_UNIQE_CODE)));
    		image = result.getBlob(result.getColumnIndex(IMAGE));  	
    	}
    	
//    	int i = 0;
//    	while( result.moveToNext() ) {
//    		Log.d("images", i++ +". "+ result.getString(0));
//    	}
//    	
    	result.close();
    	
		return image;
	}
	
	/**
	 * Delete one image row from the images table.
	 * @param imageCode the unique string to locate the image that should be deleted
	 */
	public void deleteImage( String imageCode ) {
		
		String whereCol = IMAGE_UNIQE_CODE + " = ?";
		String[] whereParam = {imageCode};
    	myDB.delete(IMAGES_TABLE_NAME,whereCol, whereParam);
	}
	
	/**
	 * Delete all the images of the given test from the local database 
	 * @param test
	 */
	public void deleteTestImgs( Test test ) {
		for ( TestChapter chapter : test.getChapters() ) {
			for ( TestItem item : chapter.getItems() ) {
				if ( item.getImage1Code() != null ) {
					deleteImage(item.getImage1Code());
				}
				
				if ( item.getImage2Code() != null ) {
					deleteImage(item.getImage2Code());
				}
			}
		}
	}
	
	public boolean isTemplateInDB( String templateTitle ) {
		Cursor templateCursor =  myDB.query(TEMPLATES_TABLE_NAME, new String[]{TEMPLATE_ID}, TITLE+" = ?", 
				new String[]{templateTitle}, null, null, null);
		
		if( templateCursor.moveToNext()) {
			templateCursor.close();
			return true;
		} else {
			templateCursor.close();
			return false;
		}
	}
	
	public boolean insertTemplateData( Template template ) {
		Cursor templateCursor =  myDB.query(TEMPLATES_TABLE_NAME, new String[]{TEMPLATE_ID}, TEMPLATE_ID+" = ?", 
				new String[]{template.getTemplateID()+""}, null, null, null);
		
		if( templateCursor.moveToNext()) {
			templateCursor.close();
			return false;
		}
		templateCursor.close();
		

		Log.e("","Insert template: " + template.getTitle());
		
    	// insert the template to a new row in the templates table
    	ContentValues templateValues = new ContentValues();
    	templateValues.put(TITLE, template.getTitle());
    	templateValues.put(TEMPLATE_ID, template.getTemplateID());
    	myDB.insert(TEMPLATES_TABLE_NAME, null, templateValues);
    	
    	// insert the template-chapters to the template_chapters table
    	for( TemplateChapter templateChapter : template.getChapters()) {
    		
    		//ArrayList<TemplateItem> templateItems = (ArrayList<TemplateItem>) templateChapter.getItems();
    		
    		ContentValues templateChapterValues = new ContentValues();
    		templateChapterValues.put(TITLE, templateChapter.getTitle());
    		templateChapterValues.put(TEMPLATE_CHAPTER_ID, templateChapter.getChapterID());
    		templateChapterValues.put(TEMPLATE_ID, template.getTemplateID());
        	myDB.insert(TEMPLATE_CHAPTERS_TABLE_NAME, null, templateChapterValues);
        	
        	// insert the templates-items to the template_items table
        	for( TemplateItem templateItem : templateChapter.getItems() ) {
        		
        		ContentValues templateItemValues = new ContentValues();
        		templateItemValues.put(ITEM_NUMBER, templateItem.getItemNumber());
        		templateItemValues.put(AREA, templateItem.getArea());
        		templateItemValues.put(TITLE, templateItem.getTitle());
        		templateItemValues.put(DESCRIPTION, templateItem.getDescription());
        		templateItemValues.put(TEMPLATE_CHAPTER_ID, templateChapter.getChapterID());
        		templateItemValues.put(TEMPLATE_ITEM_ID, templateItem.getTemplateItemID());
            	myDB.insert(TEMPLATE_ITEMS_TABLE_NAME, null, templateItemValues);
        	}
    	}
    	return true;
	}
	
	
	// excluding the first launch of the application the template for the test
	// comes from the local db.
	public Template getTemplate( String templateTitle ) {
		
		Template template = new Template();
		Log.e("","Template to find in DB : "+templateTitle);
		// query to get the template data
		Cursor templateCursor =  myDB.query(TEMPLATES_TABLE_NAME, new String[]{TEMPLATE_ID}, TITLE+"=?", 
				new String[]{templateTitle}, null, null, null);
		
		templateCursor.moveToNext();
		template.setTemplateID(templateCursor.getInt(0));
		template.setTitle(templateTitle);
		templateCursor.close();
		
		ArrayList<TemplateChapter> chapters = new ArrayList<TemplateChapter>();
		TemplateChapter chapter;
		// query to get the chapters data
		Cursor templateChaptersCursor =  myDB.query(TEMPLATE_CHAPTERS_TABLE_NAME, 
				new String[]{TEMPLATE_CHAPTER_ID,TEMPLATE_ID,TITLE}, TEMPLATE_ID+" = ?", 
				new String[]{template.getTemplateID()+""}, null, null, null);
		
		while( templateChaptersCursor.moveToNext()) {
			chapter = new TemplateChapter();
			chapter.setChapterID(templateChaptersCursor.getInt(0));
			chapter.setTemplateID(templateChaptersCursor.getInt(1));
			chapter.setTitle(templateChaptersCursor.getString(2));
			
			ArrayList<TemplateItem> items = new ArrayList<TemplateItem>();
			TemplateItem item;
			// query to get the items data
			Cursor templateitemsCursor =  myDB.query(TEMPLATE_ITEMS_TABLE_NAME, 
					new String[]{AREA,ITEM_NUMBER,TITLE,DESCRIPTION,
					TEMPLATE_CHAPTER_ID,TEMPLATE_ITEM_ID},
					TEMPLATE_CHAPTER_ID+" = ?", 
					new String[]{chapter.getChapterID()+""}, null, null, null);
			
			while( templateitemsCursor.moveToNext()) {
				item = new TemplateItem();
				item.setArea(templateitemsCursor.getString(0));
				item.setItemNumber(templateitemsCursor.getString(1));
				item.setTitle(templateitemsCursor.getString(2));
				item.setDescription(templateitemsCursor.getString(3));
				item.setChapterID(templateitemsCursor.getInt(4));
				item.setTemplateItemID(templateitemsCursor.getInt(5));
				items.add(item);
			}
			chapter.setItems(items);
			chapters.add(chapter);
		}
		
		template.setChapters(chapters);
		
		return template;
	}
	
	public void deleteTest( Test test, int testId ) {
		if( testId < 0 ) {
			Cursor testIdResult = myDB.query(TESTS_TABLE_NAME, new String[]{TEST_ID}, 
					INSTITUTION_NAME+" = ? and "+TEST_DATE+" = ?", 
					new String[]{test.getInstitutionName(),test.getDate()}, null, null, null);
			if( testIdResult.getCount() > 0 ) {
				testIdResult.moveToNext();
				testId = testIdResult.getInt(0);
			} else {
				return;
			}
		}
		
		myDB.delete(TESTS_TABLE_NAME, TEST_ID+"=?", new String[]{""+testId});
		
		Cursor chaptersToDeleteResult = myDB.query(CHAPTERS_TABLE_NAME, new String[]{CHAPTER_ID}, TEST_ID+" = ?", 
				new String[]{""+testId}, null, null, null);
		
		while( chaptersToDeleteResult.moveToNext()) {
			int chapterToDel = chaptersToDeleteResult.getInt(0);
			myDB.delete(CHAPTERS_TABLE_NAME, CHAPTER_ID+"=?", new String[]{""+chapterToDel});
			
			Cursor itemsToDeleteResult = myDB.query(TEST_ITEMS_TABLE_NAME, new String[]{TEST_ITEM_ID}, CHAPTER_ID+" = ?", 
					new String[]{""+chapterToDel}, null, null, null);
			
			while( itemsToDeleteResult.moveToNext()) {
				myDB.delete(TEST_ITEMS_TABLE_NAME, TEST_ITEM_ID+"=?", new String[]{""+itemsToDeleteResult.getInt(0)});
			}
			
			itemsToDeleteResult.close();
		}
		chaptersToDeleteResult.close();
	}
	
	
	public boolean isDBfull() {
		Cursor mCount= myDB.rawQuery("SELECT COUNT(*) FROM tests", null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		
		if( count >= TESTS_MAX_AMOUNT ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method receives a test instance and save it to the local database.
	 * The purpose of saving test is to keep the data while filling the test and in case 
	 * of network problem - save and send later to the server
	 * @param test The test to save
	 */
	public boolean addTest( Test test) {
		
		int test_index;
		
		
		Cursor isTestInDBCursor =  myDB.query(TESTS_TABLE_NAME, new String[]{TEST_ID}, 
				INSTITUTION_NAME+" = ? and "+TEST_DATE+" = ?", 
				new String[]{test.getInstitutionName(),test.getDate()}, null, null, null);
		if(isTestInDBCursor.getCount() > 0 ) {
			isTestInDBCursor.moveToNext();
			deleteTest(test,isTestInDBCursor.getInt(0));// the first param in the cursor is the test id
		} else {
			if(isDBfull()) {
				return false;
			}
		}
		
		// Insert new row to the test table (The id is an auto increment column) 
    	ContentValues testValues = new ContentValues();
    	testValues.put(INSTITUTION_NAME, test.getInstitutionName());
    	testValues.put(TEST_DATE, test.getDate());
    	testValues.put(TEMPLATE_ID, test.getTemplate().getTemplateID());
    	myDB.insert(TESTS_TABLE_NAME, null, testValues);
    	
    	// find the generated id
    	Cursor testIdResult = myDB.query(TESTS_TABLE_NAME, new String[]{TEST_ID}, INSTITUTION_NAME+" = ? and "+TEST_DATE+" = ?", 
    											new String[]{test.getInstitutionName(),test.getDate()}, null, null, null);
    	testIdResult.moveToNext();
    	test_index = testIdResult.getInt(0);
    	
    	testIdResult.close();
    	
    	
    	// insert the test-chapters to the test_chapters table 
    	for( TestChapter chapter : test.getChapters() ) {
        	ContentValues chapterValues = new ContentValues();
        	chapterValues.put(TEMPLATE_CHAPTER, chapter.getTemplate().getChapterID());
        	chapterValues.put(TEST_ID, test_index);
        	myDB.insert(CHAPTERS_TABLE_NAME, null, chapterValues);
        	
        	// find the generated id
        	Cursor chapterIdResult = myDB.query(CHAPTERS_TABLE_NAME, new String[]{CHAPTER_ID}, TEMPLATE_CHAPTER+" = ? and "+TEST_ID+" = ?", 
        											new String[]{chapter.getTemplate().getChapterID()+"",test_index+""}, null, null, null);
        	chapterIdResult.moveToNext();
        	int chapter_index = chapterIdResult.getInt(0);
        	
        	chapterIdResult.close();
        	
        	// and the test items...
        	for( TestItem item : chapter.getItems()) {
        		ContentValues itemValues = new ContentValues();
        		// first means null. 'ok' and 'first' are both boolean variables in the test item
        		// object. On the instance creation 'first' initialized to false. in the first 
        		// assignment to 'ok' (true/false), 'first' turn to be false.
        		// Because the 'ok' status can be true/false/null
        		itemValues.put(FIRST, item.isFirst()?1:0);
        		if( !item.isFirst()) {
        			itemValues.put(APPROVAL, item.isOk()?1:0);
        		}
        		
        		itemValues.put(COMMENT, item.getCommnet());
        		//itemValues.put(LEVEL, 1);
        		itemValues.put(TEMPLATE_ITEM_ID, item.getTemplateItem().getTemplateItemID());
        		itemValues.put(CHAPTER_ID, chapter_index);
        		itemValues.put(IMAGE1_CODE, item.getImage1Code());
        		itemValues.put(IMAGE2_CODE, item.getImage2Code());
        		itemValues.put(LEVEL, item.getLevelNumber());
            	myDB.insert(TEST_ITEMS_TABLE_NAME, null, itemValues);
        	}
    	}
    	
    	//setInstitutionDetails(test, test_index );
    	
    	return true;
	}
	
//	/**
//	 * This method inserts to the 'institutions' table a new row for the details of
//	 * the given test
//	 * @param test
//	 * @param testId
//	 */
//	public void setInstitutionDetails( Test test, int testId ) {
//		ArrayList<InstitutionDetail> details = 
//				(ArrayList)test.getTemplate().getInstitutionDetails();
//		Log.d("","TestId to save details for: "+ testId);
//		ContentValues detailsValues = new ContentValues();
//		detailsValues.put(INSTITUTION_ID, institutionIndex++);
//		detailsValues.put(TEST_ID, testId );
//		detailsValues.put(INSTITUTION_NAME, details.get(0).getEditText());
//		detailsValues.put(INSTITUTION_NUMBER, details.get(1).getEditText());
//		detailsValues.put(ADDRESS, details.get(2).getEditText());
//		detailsValues.put(CITY, details.get(3).getEditText());
//		detailsValues.put(PHONE_NUMBER, details.get(4).getEditText());
//		detailsValues.put(AREA, details.get(5).getEditText());
//		detailsValues.put(OWNERSHIP, details.get(6).getEditText());
//    	myDB.insert(INSTITUTIONS_TABLE_NAME, null, detailsValues);
//	}
	
	/**
	 * Returns a list of the currently saved tests
	 * @return List of the currently saved tests
	 */
	public ArrayList<Test> getTestsList() {
		
		ArrayList<Test> list = new ArrayList<Test>();
		
		// creating a cursor with all the rows in the 'tests' table
		String[] columns = {TEST_ID,INSTITUTION_NAME,TEST_DATE, TEMPLATE_ID};
    	Cursor results = myDB.query(TESTS_TABLE_NAME, columns, null, null, null, null, null);
    	
    	// loop through this cursor, create tests and add them to the list  
    	while (results.moveToNext()) {
    		Test newTest;
    		Template newTemplate;
    		int testId = results.getInt(0);
    		String institutionName = results.getString(1);
    		String date = results.getString(2);
    		int templateId = results.getInt(3);
    		
    		// find the template of the test with the template id from the test's row 
    		String[] templatesCols = { TITLE };
    		Cursor templateResult = myDB.query( TEMPLATES_TABLE_NAME, templatesCols,
    				TEMPLATE_ID+" = ?",new String[]{""+templateId},null,null,null,null);
    		templateResult.moveToNext();
    		// create a new template with the template id and the title
    		newTemplate = new Template(templateId);
    		newTemplate.setTitle(templateResult.getString(0));
    		
    		templateResult.close();
    		
    		// create cursor of the template-chapters that belongs to the specific template
    		String[] templateChapterCols = { TITLE,TEMPLATE_CHAPTER_ID, TEMPLATE_ID };
    		Cursor templateChaptersResult = myDB.query(TEMPLATE_CHAPTERS_TABLE_NAME, 
    				templateChapterCols, TEMPLATE_ID +" = ?", new String[]{""+templateId},null,null,null,null);
    		
    		// the list for the template-chapters
    		ArrayList<TemplateChapter> templateChapters = new ArrayList<TemplateChapter>();
    		
    		// create each chapter
    		while (templateChaptersResult.moveToNext()) {
    			TemplateChapter templateChapter = new TemplateChapter();
    			templateChapter.setTitle(templateChaptersResult.getString(0));
    			templateChapter.setChapterID(templateChaptersResult.getInt(1));
    			templateChapter.setTemplateID(templateChaptersResult.getInt(2));
    			
    			// find the templates-items
    			String[] templateItemCols = { ITEM_NUMBER,TITLE,AREA, DESCRIPTION,
    					TEMPLATE_CHAPTER_ID, TEMPLATE_ITEM_ID};
        		Cursor templateItemsResult = myDB.query(TEMPLATE_ITEMS_TABLE_NAME, 
        				templateItemCols, TEMPLATE_CHAPTER_ID +" = ?",new String[]{""+templateChapter.getChapterID()},null,null,null,null);

        		// list for the items
        		ArrayList<TemplateItem> templateItems = new ArrayList<TemplateItem>();
    			
        		// create each item
        		while (templateItemsResult.moveToNext()) {
        			TemplateItem templateItem = new TemplateItem();
        			templateItem.setItemNumber(templateItemsResult.getString(0));
        			templateItem.setTitle(templateItemsResult.getString(1));
        			templateItem.setArea(templateItemsResult.getString(2));
        			templateItem.setDescription(templateItemsResult.getString(3));
        			templateItem.setChapterID(templateItemsResult.getInt(4));
        			templateItem.setTemplateItemID(templateItemsResult.getInt(5));
        			
        			// add the item to the list
        			templateItems.add(templateItem);
        		}
        		
        		templateItemsResult.close();
        		
        		
        		
        		// set the new template-items list as the list of the template chapter
        		templateChapter.setItems(templateItems);
        		
        		// add the chapter to the chapters list
    			templateChapters.add(templateChapter);
    		}
    		
    		templateChaptersResult.close();
    		
    		// set the new template-chapters list as the list of the template 
    		newTemplate.setChapters(templateChapters);
    		
    		// create the test
    		newTest = new Test(newTemplate,institutionName, date);
    		newTest.setSaved(true);
    		
    		// find the test-chapters
    		String[] chapterCols = { CHAPTER_ID, TEMPLATE_CHAPTER, TEST_ID};
    		Cursor chaptersResult = myDB.query(CHAPTERS_TABLE_NAME, chapterCols, TEST_ID +" = ?",new String[]{""+testId}
    										,null,null,null,null);
    		
    		// create the test chapters list
    		ArrayList<TestChapter> chapters = new ArrayList<TestChapter>();
    		
    		// create each chapter and add it to the list
    		while (chaptersResult.moveToNext()) {
    			TestChapter newChapter;
    			int chapterId = chaptersResult.getInt(0);
    			int tChapterId = chaptersResult.getInt(1);
    			TemplateChapter templateChapter = null;
    			newChapter = new TestChapter();
    			
    			// set for each test-chapter its matching template-chapter
    			for( TemplateChapter tChapter : newTemplate.getChapters() ) {
    				if( tChapter.getChapterID() == tChapterId ) {
    					templateChapter = tChapter;
    					break;
    				}
    			}
    			
    			// Set the chapter template to the test chapter
				newChapter.setTemplate(templateChapter);
    			
				// find the test-items
    			String[] itemsCols = { APPROVAL,FIRST, COMMENT, TEMPLATE_ITEM_ID,IMAGE1_CODE,IMAGE2_CODE,LEVEL};
        		Cursor itemsResult = myDB.query(TEST_ITEMS_TABLE_NAME, itemsCols, CHAPTER_ID +" = ?",new String[]{""+chapterId},null,null,null,null);
        		
        		// create the test-items list
        		ArrayList<TestItem> items = new ArrayList<TestItem>();
        		
        		// create each test-item and add it to the list
        		while (itemsResult.moveToNext()) {
        			TestItem newItem = new TestItem();
        			int templateItemId = itemsResult.getInt(3);
        			TemplateItem templateItem = null;
        			// check if the item was not signed as first
        			if ( itemsResult.getInt(1) != 1 ) {
            			newItem.setOk(itemsResult.getInt(0)!= 0);
        			}
        			newItem.setCommnet(itemsResult.getString(2));
        			
        			// set the template for the item
        			for( TemplateItem tItem: templateChapter.getItems() ) {
        				if( tItem.getTemplateItemID() == templateItemId ) {
        					templateItem = tItem;
        					break;
        				}
        			}
        			newItem.setTemplateItem(templateItem);
        			newItem.setImage1Code(itemsResult.getString(4));
        			newItem.setImage2Code(itemsResult.getString(5));
        			newItem.setLevelNumber(itemsResult.getInt(6));
        			
        			// add the test-item to the list
        			items.add(newItem);
        		}
        		
        		itemsResult.close();
        		
        		// Set the items list as the items list of the chapter
        		newChapter.setItems(items);
        		
        		// Add the chapter to the chapters list
        		chapters.add(newChapter);
    		}
    		
    		chaptersResult.close();
    		
    		// Set the chapters list as the test's chapters list
    		newTest.setChapters(chapters);
    		// first set for initialize the details questions
//    		newTest.getTemplate().setInstitutionDetails();
//    		// second set with the same list after adding the answers from the db
//    		newTest.getTemplate().setInstitutionDetails(
//    				getInstitutionDetails( (ArrayList)newTest.getTemplate().getInstitutionDetails(), testId ));
    		list.add(newTest);
    	}
    	
    	results.close();
		
		return list;
	}
	
//	public ArrayList<InstitutionDetail> getInstitutionDetails( ArrayList<InstitutionDetail> details, int testId ) {    	
//		
//		Log.d("","TestId to find its details: "+ testId);
//		String[] detailsCols = { INSTITUTION_NAME, INSTITUTION_NUMBER, ADDRESS,
//									CITY, PHONE_NUMBER, AREA, OWNERSHIP};
//																									
//		Cursor detailsResult = myDB.query(INSTITUTIONS_TABLE_NAME, detailsCols, TEST_ID +" = ?",
//					new String[]{""+testId},null,null,null,null);
//		
//		detailsResult.moveToNext();
//		if ( detailsResult.getCount() > 0 ) {  
//			details.get(0).setEditText( detailsResult.getString(0));
//			details.get(1).setEditText( detailsResult.getString(1));
//			details.get(2).setEditText( detailsResult.getString(2));
//			details.get(3).setEditText( detailsResult.getString(3));
//			details.get(4).setEditText( detailsResult.getString(4));
//			details.get(5).setEditText( detailsResult.getString(5));
//			details.get(6).setEditText( detailsResult.getString(6));
//		}
//		
//		detailsResult.close();
//		return details;
//	}

}
