package nothelloworld.sociallive;

import android.R;
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
import android.widget.ImageButton;

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



    }

    public void savePreferences(View view){
        SharedPreferences.Editor theEditor = userPreferences.edit();
        theEditor.putString("username", String.valueOf(findViewById(R.id.user)));
        theEditor.commit();
    }

    public void getAndShowPreferences(View v) {
        String result = "hello";

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = userPreferences.edit();
        prefEditor.putString("username", result);
        // store username from text editor in the login page.
        //SharedPreferences.Editor theEditor = userPreferences.edit();
        // WARNING: ERROR IS HERE
        //EditText edit = (EditText)findViewById(R.id.user);
        prefEditor.apply();
        //String result = edit.getText().toString();
        //theEditor.putString("username", result);

        createEventDialog.dismiss();
    }

    public void launchFindEventWindow(View v) {

        //move overlay on screen by animating the x value
        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
    }

    public void createEvent(View v) {

        // launch dialog activity
        String userName = userPreferences.getString("username", "nothinghere");

        if (userName == "nothinghere") {
            createEventDialog.setContentView(R.layout.loginpopup);
            createEventDialog.show();
        }
        else {
            createEventDialog.setContentView(R.layout.createpartypopup);
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
}
