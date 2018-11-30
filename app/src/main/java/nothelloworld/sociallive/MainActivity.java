package nothelloworld.sociallive;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

/**
 * EVERYTHING IS HERE
 */
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

    // These are variables for saving parties into our real-time database
    private EditText locationOfParty;
    EditText descriptionOfParty;
    EditText startTimeOfParty;
    EditText endTimeOfParty;
    EditText dateOfParty;
    Button buttonCreateParty;


    DatabaseReference databaseParties;
    /**
     * On create. Create all of the backbone of what we are doing by creating and
     * initializing the vaiables.
     * @param savedInstanceState
     */
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

        // connect to the real-time database
        databaseParties = FirebaseDatabase.getInstance().getReference("parties");

        createEventDialog.setContentView(R.layout.createpartypopup);

        // create variables to store any parties the user might create in the database
        locationOfParty = (EditText) createEventDialog.findViewById(R.id.eventLocation);
        descriptionOfParty = (EditText) createEventDialog.findViewById(R.id.eventDescription);
        startTimeOfParty = (EditText) createEventDialog.findViewById(R.id.eventStartTime);
        endTimeOfParty = (EditText) createEventDialog.findViewById(R.id.eventEndTime);
        dateOfParty = (EditText) createEventDialog.findViewById(R.id.eventDate);
        buttonCreateParty = createEventDialog.findViewById(R.id.createEventButton);

       // Log.d("Main Activity", "location of party: "+locationOfParty);

        // when the button is pressed, we add the party to the database
        buttonCreateParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPartyToDatabase();
            }
        });



    }

    /**
     * The user has just created a username and hit save. Now let's save their username
     * and then launch the create event window.
     * @param v
     */
    public void createIdentification(View v) {

        EditText edit = (EditText)createEventDialog.findViewById(R.id.user);
        String result = edit.getText().toString();

        Log.d("Main Activity", "Storing this into shared preferences "+result);

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = userPreferences.edit();
        prefEditor.putString("username", result);
        Log.v("A message", "Hello?!");
        // store username from text editor in the login page.

        prefEditor.apply();

        createEventDialog.dismiss();

        // now return us to the window that can create an event.
        createEventDialog.setContentView(R.layout.createpartypopup);
        createEventDialog.show();
    }

    /*******************************************************************************
     * TODO:
     * We will work on this later.
     *******************************************************************************/
    /**
     * The goal of this is to populate the feed window with the most popular events.
     */
    public void populatePopularFeed() {

    }

    /**
     * Launch the find event window, animate it onto the screen.
     * @param v
     */
    public void launchFindEventWindow(View v) {

        //move overlay on screen by animating the x value
        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
    }

    /**
     * Launch the create event window. But first, check if the user is signed in, with
     * their username and password saved into Shared Preferences.
     * @param v
     */
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

    /**
     * TODO: Make the pop up (x) buttons clickable
     * Dismiss the pop up to create an event.
     * @param v
     */
    public void returnToFeedFromCreateEventPopUp(View v) {

        // Dismiss the pop up window
        addPartyToDatabase();
        createEventDialog.dismiss();
    }

    /**
     * Launch the Board Game Suggestions page
     * @param v
     */
    public void toBoardGameSuggestions(View v) {

        // first things first, get the current window off the screen.
        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);

        // move overlay
        categoriesLayout.animate().x(0).setDuration(animationDuration);
    }

    /**
     * Launch the Video Game Suggestion page.
     * @param v
     */
    public void toVideoGameSuggestions(View v) {

    }

    /**
     * Return to the feed from the categories page, by pushing off the categories page
     * as well as the event find page.
     * @param v
     */
    public void returnToFeedFromCategoriesPage(View v) {

        // Push the categories page off the screen
        categoriesLayout.animate().x(categoriesLayout.getWidth()).setDuration(animationDuration);

        // if the find events page is still on screen, push it off.
        if (eventCategoryFinder.getX() == 0)
            eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
    }

    private void addPartyToDatabase() {

        //TODO: add these EditText and delete above!!
        //EditText locationOfParty = (EditText) createEventDialog.findViewById(R.id.eventLocation);
        String location = locationOfParty.getText().toString().trim();
        String description = descriptionOfParty.getText().toString().trim();
        String startTime = startTimeOfParty.getText().toString().trim();
        String endTime = endTimeOfParty.getText().toString().trim();
        String date = dateOfParty.getText().toString().trim();
        String dateCreated = new java.util.Date().toString();

        Log.d("Main Activity", "location: "+location);

        if (!TextUtils.isEmpty(location)) {
            String id = databaseParties.push().getKey();

            Party party = new Party(location, description, startTime, endTime,
                    date, dateCreated, id);

            databaseParties.child(id).setValue(party);

            Toast.makeText(this, "Party Created", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "You must set a location", Toast.LENGTH_LONG).show();
        }
    }
}
