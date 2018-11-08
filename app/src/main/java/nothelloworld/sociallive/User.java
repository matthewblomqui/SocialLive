package nothelloworld.sociallive;

import android.location.Location;

public class User {

    private String userName;
    private String password;
    private Location location;
    private String bio;

    public User(String userName, String password, String bio) {
        this.userName = userName;
        this.password = password;
        this.bio = bio;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
