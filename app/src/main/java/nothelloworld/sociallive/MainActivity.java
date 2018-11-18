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
    Dialog createLogin;

    /***********************************************************************
     * On create method. We create all of our variables that will be used
     * throughout the program.
     **********************************************************************/
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

        // declare Dialog variable for pop up window
        createEventDialog = new Dialog(this);
        createLogin = new Dialog(this);



    }

    /************************************************************************
     * This corresponds to our login page. This is the onclick method for the
     * user to submit their username in order to be able to make an event.
     ************************************************************************/
    public void createIdentification(View v) {

        EditText edit = (EditText)createLogin.findViewById(R.id.user);
        String result = edit.getText().toString();

        // Save the user entered username into the Shared Preferences.
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = userPreferences.edit();
        prefEditor.putString("username", result);

        prefEditor.apply();
        //theEditor.putString("username", result);

        createLogin.dismiss();
    }

    /***********************************************************************
     * Launch the find Event Window by moving that window on top of our
     * feed window.
     **********************************************************************/
    public void launchFindEventWindow(View v) {

        //move overlay on screen by animating the x value
        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
    }

    /**********************************************************************
     * Check if we already have the username stored into user preferences,
     * and if not, launch the sign up window. If there is a username,
     * launch the create event ui page.
     **********************************************************************/
    public void createEvent(View v) {

        // launch dialog activity
        String userName = userPreferences.getString("username", "nothinghere");

        if (userName.equals("nothinghere")) {
            createLogin.setContentView(R.layout.loginpopup);
            createLogin.show();
        }
        else {
            createEventDialog.setContentView(R.layout.createpartypopup);
            EditText p = (EditText)createEventDialog.findViewById(R.id.editText2);
            p.setText(userName);
            createEventDialog.show();
        }
    }

    /*******************************************************************
     * Return to the feed from the create event dialog. Do this by
     * simply dismissing the dialog that is currently open.
     *******************************************************************/
    public void returnToFeed(View v) {

        // Dismiss the pop up window
        createEventDialog.dismiss();
    }

    /*******************************************************************
     * This will launch the window that will bring over the top the
     * suggestions for board games
     *******************************************************************/
    public void toBoardGameSuggestions(View v) {

        // move overlay
        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
    }

    /*******************************************************************
     * This will launch the video game suggestions feed.
     *
     *******************************************************************/
    public void toVideoGameSuggestions(View v) {

    }

    /**************************************************************
     *  If the below function is run, we are on the page listing
     *  the the different categories. We want to go back to the feed
     *  Do this by using object animator for all the different windows.
     *  And making the Categories page invisible again.
     **************************************************************/
    public void returnToFeedFromCategoriesPage(View view) {

        // animate the suggested categories pages off the screen to get back to the page.
        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(0);
        categoriesLayout.animate().x(eventCategoryFinder.getWidth()).setDuration(0);
    }
}
