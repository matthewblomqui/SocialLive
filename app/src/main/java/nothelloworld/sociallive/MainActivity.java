package nothelloworld.sociallive;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    //this is a comment
    private boolean findEventButtonOnScreen;
    private boolean sportsEventButtonOnScreen;

    private ImageButton joinEvent;
    private ImageButton sportsEvent;

    // There were two Handler classes available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinEvent = (ImageButton)findViewById(R.id.FindEvent);
        sportsEvent = (ImageButton)findViewById(R.id.sportsButton);
        findEventButtonOnScreen = true;
        sportsEventButtonOnScreen = false;
    }

    public void findEventType(View view) {
        
        // get screen size
        if (findEventButtonOnScreen) {
            // move even button off the screen
            int moveAmount = getWindow().getDecorView().getRootView().getMeasuredWidth();
            ObjectAnimator moveOffAnimation = ObjectAnimator.ofFloat(joinEvent, "translationX", moveAmount);
            moveOffAnimation.setDuration(500);
            moveOffAnimation.start();
            findEventButtonOnScreen = false;

            if (!sportsEventButtonOnScreen) {
                // move the sports button on the screen
                ObjectAnimator sportsMoveOffAnimation = ObjectAnimator.ofFloat(sportsEvent, "translationX", 0f);
                sportsMoveOffAnimation.setDuration(500);
                sportsMoveOffAnimation.start();
                sportsEventButtonOnScreen = true;
            }
        }
        // how do i make the button bring another button on the screen?
        // how do I initialize background color of screen to different color?
        // can we make haze backgrounds when a certain image view is on?
        // there isn't really an onscreen or offscreen function.. how does that work in java?
    }

    public void findSportsEvent(View view) {
        if (!findEventButtonOnScreen) {
            // move event button back on screen
            ObjectAnimator moveOffAnimation = ObjectAnimator.ofFloat(joinEvent, "translationX", 0f);
            moveOffAnimation.setDuration(500);
            moveOffAnimation.start();
            findEventButtonOnScreen = true;

            if (sportsEventButtonOnScreen) {
                // move the sports events button off the screen
                int moveAmount = getWindow().getDecorView().getRootView().getMeasuredWidth();
                ObjectAnimator sportsMoveOffAnimation = ObjectAnimator.ofFloat(sportsEvent, "translationX", moveAmount);
                sportsMoveOffAnimation.setDuration(500);
                sportsMoveOffAnimation.start();
                sportsEventButtonOnScreen = false;
            }
        }
    }
}
