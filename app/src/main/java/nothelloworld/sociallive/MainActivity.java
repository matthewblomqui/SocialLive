package nothelloworld.sociallive;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences userPreferences;

    // this will populate the popular section of our feed
    private List<Party> popularEvents;

    // have to do with animations regarding the different windows.
    public long animationDuration = 1000;
    private ConstraintLayout eventCategoryFinder = null;
    private LinearLayout categoriesLayout = null;

    // This is the pop up window to create a new event
    Dialog createEventDialog;

    /*********************************************************************************
     * On create. Create all of the backbone of what we are doing by creating and
     * initializing the variables.
     * **********************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);

        // find the overlay
        eventCategoryFinder = findViewById(R.id.findEventWindow);
        categoriesLayout = findViewById(R.id.categoriesLayout);

        // Get the size of the screen/window
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // translate the new window off screen without animation before anyone can see it
        eventCategoryFinder.setX(size.x);
        categoriesLayout.setX(size.x);


        // declare Dialog variable for pop up window
        createEventDialog = new Dialog(this);
    }

    /*********************************************************************************
     * The user has just created a username and hit save. Now let's save their
     * username and then launch the create event window.
     * **********************************************************************************/
    public void createIdentification(View v) {

        EditText edit = (EditText)createEventDialog.findViewById(R.id.user);
        String result = edit.getText().toString();

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = userPreferences.edit();
        prefEditor.putString("username", result);
        // store username from text editor in the login page.

        prefEditor.apply();

        createEventDialog.dismiss();

        // now return us to the window that can create an event.
        createEventDialog.setContentView(R.layout.createpartypopup);
        createEventDialog.show();
    }

    /*******************************************************************************
     * We will work on this later. The goal of this is to populate the feed
     * window with the most popular events.
     *******************************************************************************/
    public void populatePopularFeed() {

    }

    /*********************************************************************************
     * Launch the find event window, animate it onto the screen.
     * then we are done! Pretty simple ay?
     * **********************************************************************************/
    public void launchFindEventWindow(View v) {

        //move overlay on screen by animating the x value
        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
    }

    /*********************************************************************************
     * Launch the create event window. But first, check if the user is signed in, with
     * their username and password saved into Shared Preferences.
     * **********************************************************************************/
    public void createEvent(View v) {

        // launch dialog activity
        String userName = userPreferences.getString("username", "nothinghere");

        if (userName.equals("nothinghere")) {
            createEventDialog.setContentView(R.layout.loginpopup);
            createEventDialog.show();
        }
        else {
            createEventDialog.setContentView(R.layout.createpartypopup);
            createEventDialog.show();
        }
    }

    /*********************************************************************************
     * Simply dismiss the pop up to create an event.
     *
     * **********************************************************************************/
    public void returnToFeedFromCreateEventPopUp(View v) {

        // Dismiss the pop up window
        createEventDialog.dismiss();
    }

    /*********************************************************************************
     * Launch the Board Game Suggestions page
     *
     * **********************************************************************************/
    public void toBoardGameSuggestions(View v) {

        // first things first, get the current window off the screen.
        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);

        // move overlay
        categoriesLayout.animate().x(0).setDuration(animationDuration);
    }

    /*********************************************************************************
     * This will launch the video game suggestions page.
     *
     * **********************************************************************************/
    public void toVideoGameSuggestions(View v) {

    }

     /*********************************************************************************
     * Return to the feed from the categories page, by pushing off the categories page
     * as well as the event find page.
     * **********************************************************************************/
    public void returnToFeedFromCategoriesPage(View v) {

        // Push the categories page off the screen
        categoriesLayout.animate().x(categoriesLayout.getWidth()).setDuration(animationDuration);

        // if the find events page is still on screen, push it off.
        if (eventCategoryFinder.getX() == 0)
            eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
    }
}
