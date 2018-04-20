package data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * An instance of this class represent the data that gets transferred through the stream
 * between the android client application to the java server application.
 * The dataType field is the indication for the applications how to use the data.
 * @author Shira Elitzur
 */
public class Data implements Serializable {
    /**
     * This data type used when the client send a request to the 
     * server to get the data of a specific template
     */
    public final static int TEMPLATE_REQUEST_DT = 1;
    /**
     * This data type used when the server send a response to the 
     * client with the data of the template he asked for
     */
    public final static int TEMPLATE_RESPONSE_DT = 2;
    /**
     * This data type used when the client sent the completed test
     * object to the server
     */
    public final static int TEST_DT = 3;
    /**
     * This data type used when the client send a request to login
     */
    public final static int LOGIN_REQUEST_DT = 0;
    /**
     * This data type used when the server send a response to the 
     * client's login request 
     */
    public final static int LOGIN_RESPONSE_DT = -1;
    public final static int CLOSE_SOCKET = 2371;
    public final static int INSERT_TEST_DT = 23;
    public final static int INSERT_CHAPTER_DT = 20;
    public final static int INSERT_ITEM_DT = 22;
    public final static int CLOSE_SQL_CONNECTION = 2222;
    public final static int ITEMS_RESULT_SET_REQUEST_DT = 5;
    public final static int USERS_RESULT_SET_REQUEST_DT = 7;
    public final static int TEMPLATE_TITLES_LIST_REQUEST_DT = 6;
    public final static int CHAPTER_TITLES_LIST_REQUEST_DT = 8;
    public final static int TESTS_ARRAY_REQUEST_DT = 9;
    public final static int GET_TEST_BY_ID_REQUEST_DT = 10;
    public final static int GET_TEST_ITEM_BY_ID_REQUEST_DT = 17;
    public final static int UPDATE_ITEM_REQUEST_DT = 11;
    public final static int INSERT_ITEM_REQUEST_DT = 12;
    public final static int DELETE_ITEM_REQUEST_DT = 13;
    public final static int UPDATE_USER_REQUEST_DT = 14;
    public final static int INSERT_USER_REQUEST_DT = 15;
    public final static int DELETE_USER_REQUEST_DT = 16;
    public final static int LOGIN_DESKTOP_APPLICATION = 18;
    /**
     * The data-type of this Data instance. One of the final static data-type 
     * that defined in this class
     */
    private int dataType;
    
    /**
     * For data-type request to login the userName will be stored in this field
     */
    private String userName;
    /**
     * For data-type request to login the password will be stored in this field
     */
    private String password;
    /**
     * For data-type response to login request this filed will indicate if the user is 
     * familiar to the system
     */
    private boolean isUser;
    /**
     * When the data-type is request for template this field will indicate 
     * which template the client wants
     */
    private int templateID;

	/**
     * When the data-type is response-template the template is 
     * stored in this field
     */
    private Template template;
    /**
     * When the data type is test the test is stored in this field
     */
    private Test test;
    private ArrayList arrayList;
    private TemplateItem item;
    private int itemId;
    private String chapter;
    private String templateName;
    private ResultSet resultSet;
    private User user;
    private String error;
    private int userId;
    private Object[][] objectMatrix;
    private int testId;
    private int chapterId;
    private Object objecto;
    
    /**
     * Creates a new Data instance with the given data type
     * @param dataType dataType value will be one of the final static 
     * data-types in this class
     */
    public Data( int dataType ) {
        setDataType(dataType);
    }
    
    /**
     * Returns the data type of this Data instance
     * @return
     */
    public int getDataType() {
        return dataType;
    }
    
    /**
     * Sets the data type for this data instance
     * @param dataType
     */
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    
    /**
     * Returns the template id of this data instance. Used when the data-type is-
     * TEMPLATE_REQUEST_DT
     * @return
     */
    public int getTemplateID() {
        return templateID;
    }
    
    /**
     * Sets the template id for this data instance. Used when the data-type is-
     * TEMPLATE_REQUEST_DT
     * @param templateID
     */
    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }
    
    /**
     * Returns the template (test-template) of this data instance.
     * Used when the data-type is TEMPLATE_RESPONSE_DT
     * @return
     */
    public Template getTemplate() {
        return template;
    }
    
    /**
     * Sets the template (test-template) for this data instance.
     * Used when the data-type is TEMPLATE_RESPONSE_DT
     * @param template
     */
    public void setTemplate(Template template) {
        this.template = template;
    }
    
    /**
     * Returns the test item of this data instance.
     * Used when the data type is TEST_DT
     * @return
     */
    public Test getTest() {
		return test;
	}
    
    /**
     * Sets the test item for this data instance.
     * Used when the data type is TEST_DT
     * @param test
     */
    public void setTest(Test test) {
            this.test = test;
    }

    /**
     * Returns the userName of this data instance.
     * Used when the data type is LOGIN_REQUEST_DT
     * @return
     */
    public String getUserName() {
            return userName;
    }

    /**
     * Sets the userName for this data instance.
     * Used when the data type is LOGIN_REQUEST_DT
     * @param userName
     */
    public void setUserName(String userName) {
            this.userName = userName;
    }

    /**
     * Returns the password of this data instance.
     * Used when the data type is LOGIN_REQUEST_DT
     * @return
     */
    public String getPassword() {
            return password;
    }

    /**
     * Sets the password for this data instance.
     * Used when the data type is LOGIN_REQUEST_DT
     * @param userName
     */
    public void setPassword(String password) {
            this.password = password;
    }

    /**
     * Returns true if the requested user is an authorized user from the server db.
     * Used when the data type is LOGIN_RESPONSE_DT
     * @return
     */
    public boolean isUser() {
            return isUser;
    }

    /**
     * Sets whether the user is an authorized user in the server db.
     * Used when the data type is LOGIN_RESPONSE_DT
     * @param isUser
     */
    public void setUser(boolean isUser) {
            this.isUser = isUser;
    }
    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public TemplateItem getItem() {
        return item;
    }

    public void setItem(TemplateItem item) {
        this.item = item;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object[][] getObjectMatrix() {
        return objectMatrix;
    }

    public void setObjectMatrix(Object[][] objectMatrix) {
        this.objectMatrix = objectMatrix;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public Object getObjecto() {
        return objecto;
    }

    public void setObjecto(Object objecto) {
        this.objecto = objecto;
    }
}
