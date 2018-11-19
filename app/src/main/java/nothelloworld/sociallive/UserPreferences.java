package nothelloworld.sociallive;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {

    List<String> userPreferences;

    UserPreferences(List<String> userPreferences) {
        // set the user preferences in the default constructor
        this.userPreferences = userPreferences;
    }

    public List<String> getUserPreferences() { return userPreferences; }

    public void addToPreferences(String addMe) {

        if (userPreferences == null) {
            userPreferences = new ArrayList<>();
        }

        userPreferences.add(addMe);

    }

    public void setUserPreferences(List<String> userPreferences) { this.userPreferences = userPreferences; }
}
