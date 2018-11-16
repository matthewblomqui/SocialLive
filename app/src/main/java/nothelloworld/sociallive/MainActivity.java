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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences userPreferences;

    // this will populate the popular section of our feed
    private List<Party> popularEvents;

    // have to do with animations regarding the different windows.
    public long animationDuration = 1000;
    private ConstraintLayout eventCategoryFinder = null;

    // This is the pop up window to create a new event
    Dialog createEventDialog;
    Dialog createLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);

        // find the overlay
        eventCategoryFinder = findViewById(R.id.findEventWindow);

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

    public void createIdentification(View v) {

        EditText edit = (EditText)createLogin.findViewById(R.id.user);
        String result = edit.getText().toString();

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = userPreferences.edit();
        prefEditor.putString("username", result);
        // store username from text editor in the login page.
        //SharedPreferences.Editor theEditor = userPreferences.edit();
        // WARNING: ERROR IS HERE

        prefEditor.apply();
        //theEditor.putString("username", result);

        createLogin.dismiss();
    }

    public void launchFindEventWindow(View v) {

        //move overlay on screen by animating the x value
        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
    }

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

    public void returnToFeed(View v) {

        // Dismiss the pop up window
        createEventDialog.dismiss();
    }

    public void toBoardGameSuggestions(View v) {

        // move overlay
        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
    }

    public void toVideoGameSuggestions(View v) {

    }

    /**************************************************************
     *  If the below function is run, we are on the page listing
     *  the the different categories. We want to go back to the feed
     *  Do this by using object animator for all the different windows.
     *  And making the Categories page invisible again.
     **************************************************************/
    public void returnToFeedFromCategoriesPage(View view) {
        // Finish this later
    }
}
