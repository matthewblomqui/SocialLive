package nothelloworld.sociallive;

/**
 * Class for Users
 * Has a userName and password
 */
public class User {

    private String userName;

    private String password;

    /**
     * Non-Default Constructor
     * @param userName
     * @param password
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Getter for userName
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for userName
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for password
     * @return String
     */
    public String getPassword() { return password; }

    /**
     * Setter for password
     * @param password
     */
    public void setPassword(String password) { this.password = password; }
}
