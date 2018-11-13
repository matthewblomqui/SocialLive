package nothelloworld.sociallive;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    // this will populate the popular section of our feed
    private List<Party> popularEvents;

    // have to do with animations regarding the different windows.
    public long animationDuration = 1000;
    private ConstraintLayout eventCategoryFinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find the overlay
        eventCategoryFinder = findViewById(R.id.findEventWindow);

        // Get the size of the screen/window
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // translate the new window off screen without animation before anyone can see it
        eventCategoryFinder.setX(size.x);
    }

    public void launchFindEventWindow(View v) {

        //move overlay on screen by animating the x value
        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
    }

    public void createEvent(View v) {

    }

    public void toBoardGameSuggestions(View v) {

        // move overlay
        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
    }

    public void toVideoGameSuggestions(View v) {

    }
}
