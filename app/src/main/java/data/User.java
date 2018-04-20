package data;

import data.Test;
import java.io.Serializable;
import java.util.List;

/**
 * User is a tester who follow a complex list of conditions for the institution.
 * @author Shira Elitzur
 */
public class User implements Serializable {
    /**
     * The first name of this user.
     */
    private String firstName;
    /**
     * The last name of this user.
     */
    private String lastName;
    /**
     * The ID number of this user(uniqe).
     */
    private int ID;


    /**
     * The user's username for the mobile application.
     */
    private String userName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * The user's password for the mobile application.
     */
    private String password;
    /**
     * The privilege level of this user.<br>
     * In the different levels users can choose other type of tests to fill.
     */
    private int privilegeLevel;
    /**
     * The Tests that this user filled.
     */
    private List<Test> tests;
    
    /**
     * Creates a new user of the mobile application.<br>
     * @param ID The ID uniqe number for the user
     * @param name The name of the user
     * @param level The privilege level for the user, 
     * determines which tests he is going to work with.
     */
    public User() {
        
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
    /**
     * Sets the name of this user to the specified name.
     * @param name The name for this user
     */
    public void setName( String firstName, String lastName ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Returns the first name of this user.
     * @return The first name of this user.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Returns the last name of this user.
     * @return The last name of this user.
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Returns the username of this user for the mobile application
     * @return The username of this user
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Returns the password of this user to the mobile application
     * @return The password of this user
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the level of this user to the specified level.
     * @param level The level for this user.
     */
    public void setPrivilegeLevel( int level ) {
        privilegeLevel = level;
    }
    
    /**
     * Returns the privileged level of this user.
     * @return The privileged level of this user
     */
    public int getPrivilegeLevel() {
        return privilegeLevel;
    }
    
    /**
     * Return the ID of this user.
     * @return The ID of this user
     */
    public int getID() {
        return ID;
    }
}
