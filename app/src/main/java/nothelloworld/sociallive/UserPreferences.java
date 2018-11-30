package nothelloworld.sociallive;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Search history for users to help give suggestions for events to pick.
 *
 */
public class UserPreferences {

    List<String> userPreferences;

    /**
     * Default Constructor
     * @param userPreferences
     */
    UserPreferences(List<String> userPreferences) {
        // set the user preferences in the default constructor
        this.userPreferences = userPreferences;
    }

    /**
     *
     * @return list of Strings
     */
    public List<String> getUserPreferences() { return userPreferences; }

    /**
     * Insert a user preference.
     * @param addMe
     */
    public void addToPreferences(String addMe) {

        if (userPreferences == null) {
            userPreferences = new ArrayList<>();
        }

        userPreferences.add(addMe);

    }

    /**
     * A setter for userPreferences
     * @param userPreferences
     */
    public void setUserPreferences(List<String> userPreferences) { this.userPreferences = userPreferences; }
}
