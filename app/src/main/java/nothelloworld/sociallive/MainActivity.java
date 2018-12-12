package nothelloworld.sociallive;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 * TODO: <MYEVENTSFRAGMENT>Dialog for viewing events (myEvents and events going to)
 * TODO: <CREATEEVENTFRAGMENT>copy past create event page and add picture capability from database
 * TODO: <SEARCHFRAGRMENT>copy past search page, add dialog pop up for categories
 * TODO: <HOME> implement recycle view
 * TODO: Search bar and slide up with that bar
 * TODO: Pulling from and pushing with the database (emphasize images and pulling data)
 * TODO: User authentication with firebase
 */
public class MainActivity extends AppCompatActivity {


    /**
     * Copied from create event fragment
     */
    private DatabaseReference databaseParties;

    // variables for choosing images from your phone
    private static final int PICK_IMAGE_REQUEST = 1;
    //private TextView mTextViewShowUploads;
    //private EditText mEditTextFileName;
    private ImageView mImageView;

    private Uri mImageUri;

    private StorageReference mStorageRef;

    /**
     * Not comment for below code
     * Just a place holder for code above
     */
    private SharedPreferences userPreferences;

    // this will populate the popular section of our feed
    private List<Party> popularEvents;

    // have to do with animations regarding the different windows.
    public long animationDuration = 1000;
    private LinearLayout eventCategoryFinder = null;
    private LinearLayout categoriesLayout = null;

    // This is the pop up window to create a new event
    Dialog createEventDialog;

    //DatabaseReference databaseParties;
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

        mImageView = findViewById(R.id.image_view);

        // find the overlay
        //eventCategoryFinder = findViewById(R.id.findEventWindow);
        //categoriesLayout = findViewById(R.id.categoriesLayout);

        // Get the size of the screen/window
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // translate the new window off screen without animation before anyone can see it
        //eventCategoryFinder.setX(size.x);
        //categoriesLayout.setX(size.x);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // declare Dialog variable for pop up window
        createEventDialog = new Dialog(this);

        // connect to the real-time database
        databaseParties = FirebaseDatabase.getInstance().getReference("parties");

        createEventDialog.setContentView(R.layout.createpartypopup);

        // Choose the Home screen fragment at the beginning of creation
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch(menuItem.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.nav_find:
                        selectedFragment = new FindEventFragment();
                        break;

                    case R.id.nav_create:

                        selectedFragment = new CreateEventFragment();
                        //selectedFragment = new HomeFragment();
                        //createEvent();
                        break;

                    case R.id.nav_my_events:
                        selectedFragment = new MyEventFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        };

    /**
     * TODO: Database stuff.......................................................................
     * Copied from create event fragment
     */
    // used to get the file extension from the image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /**
     * Create a new party and add it to the database.
     * @return true if the user entered valid credentials. If not, then don't.
     */
    public void addPartyToDatabase(View v) {

        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("WrongViewCast") View layout = inflater.inflate(R.layout.createpartypopup,
                (ViewGroup) MainActivity.this.findViewById(R.id.eventLocation));

        EditText locationOfParty = (EditText) layout.findViewById(R.id.eventLocation);
        EditText descriptionOfParty = (EditText) v.findViewById(R.id.eventDescription);
        EditText startTimeOfParty = (EditText) v.findViewById(R.id.eventStartTime);
        EditText endTimeOfParty = (EditText) v.findViewById(R.id.eventEndTime);
        EditText dateOfParty = (EditText)  v.findViewById(R.id.eventDate);

        final String location = locationOfParty.getText().toString().trim();
        final String description = descriptionOfParty.getText().toString().trim();
        final String startTime = startTimeOfParty.getText().toString().trim();
        final String endTime = endTimeOfParty.getText().toString().trim();
        final String date = dateOfParty.getText().toString().trim();
        final String dateCreated = new java.util.Date().toString();

        //for uploading an image
        if (mImageUri != null) {

        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

        // working on pattern checking for location
        if (!checkDateString(date)) {
            Toast.makeText(this, "Put in a valid Date!", Toast.LENGTH_SHORT).show();
            return/* false*/;
        }
        if (!checkStartTime(startTime) || !checkStartTime(endTime)) {
            Toast.makeText(this, "Put in a valid start time!", Toast.LENGTH_SHORT).show();
            return/* false*/;
        }

        Log.d("Main Activity", "location: "+location);

        if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(description) && mImageUri != null) {
            // for storing the image. This creates a unique id for the image
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            // check the progress of adding our image into storage
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();

                            final String id = databaseParties.push().getKey();

                            // for getting the URL of our image to save in the database
                            Task<Uri> uploadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                            uploadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mImageUri = uri;

                                    Party party = new Party(location, description, startTime, endTime,
                                            date, dateCreated, id, mImageUri.toString());

                                    // used to push the party into the database
                                    //String uploadId = databaseParties.push().getKey();
                                    databaseParties.child(id).setValue(party);

                                    Toast.makeText(getApplicationContext(), "Party Created", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        }
                    });

        }
        else if (mImageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return/* false*/;
        }
        else {
            Toast.makeText(this, "You must set a location", Toast.LENGTH_LONG).show();
            return/* false*/;
        }
        return/* true*/;
    }

    public void openFileChooser(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            mImageUri = data.getData();

            mImageView = findViewById(R.id.image_view);

            Picasso.with(this).load(mImageUri).into(mImageView);
            mImageView.setImageURI(mImageUri);
        }
    }
    /**
     * Not comment for below code
     * Just a place holder for code above
     */

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
     * TODO: from above list (recycle view)
     * We will work on this later.
     *******************************************************************************/
    /**
     * The goal of this is to populate the feed window with the most popular events.
     */
//    public void populatePopularFeed() {
//
//    }

    /**
     * Launch the find event window, animate it onto the screen.
     * @param v
     */
//    public void launchFindEventWindow(View v) {
//
//        //move overlay on screen by animating the x value
//        eventCategoryFinder.animate().x(0).setDuration(animationDuration);
//    }

    /**
     * Launch the create event window. But first, check if the user is signed in, with
     * their username and password saved into Shared Preferences.
     * @param
     */
    public void createEvent(/*View v*/) {

        // launch dialog activity
        String userName = userPreferences.getString("username", "nothinghere");

        if (userName.equals("nothinghere")) {
            createEventDialog.setContentView(R.layout.loginpopup);
            createEventDialog.show();
        }
        else {
            createEventDialog.setContentView(R.layout.createpartypopup);
            createEventDialog.show();
            mImageView = findViewById(R.id.image_view);
        }
    }

    /**
     * Dismiss the pop up to create an event.
     * @param v
     */
//    public void returnToFeedFromCreateEventPopUp(View v) {
//
//        // Dismiss the pop up window
//        if (addPartyToDatabase())
//            createEventDialog.dismiss();
//    }

    /**
     * Return to the feed from the categories page, by pushing off the categories page
     * as well as the event find page.
     * @param v
     */
//    public void returnToFeedFromCategoriesPage(View v) {
//
//        // Push the categories page off the screen
//        categoriesLayout.animate().x(categoriesLayout.getWidth()).setDuration(animationDuration);
//
//        // if the find events page is still on screen, push it off.
//        if (eventCategoryFinder.getX() == 0)
//            eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
//    }

    /**
     * Return from find event page back to first page
     * @param v
     */
//    public void backToFrontPage(View v) {
//        // move overlay
//        eventCategoryFinder.animate().x(eventCategoryFinder.getWidth()).setDuration(animationDuration);
//    }

    /**
     *
     * @param date String that is what the user entered for the date in the create event page
     * @return true if the date has
     */
    public boolean checkDateString(String date) {

        for (int i = 0; i < 2; i++)
            if (!Character.isDigit(date.charAt(i))) return false;

        if (date.charAt(2) != '-') return false;

        for (int i = 3; i < 5; i++)
            if (!Character.isDigit(date.charAt(i))) return false;

        return true;
    }

    /**
     *
     * @param time is the string that the user specifies regarding time of the event
     * @return
     */
    public boolean checkStartTime(String time) {

        if (time.length() != 7) return false;

        for (int i = 0; i < 2; i++)
            if (!Character.isDigit(time.charAt(i))) return false;

        if (time.charAt(2) != ':') return false;

        for (int i = 3; i < 5; i++)
            if (!Character.isDigit(time.charAt(i))) return false;

        String beyondMidnight = time.substring(5);

        return (!beyondMidnight.equals("AM") && !beyondMidnight.equals("PM"));
    }

    /**
     * Create a new party and add it to the database.
     * @return true if the user entered valid credentials. If not, then don't.
     */
//    private boolean addPartyToDatabase(View v) {
//
//        EditText locationOfParty = (EditText) findViewById(R.id.eventLocation);
//        EditText descriptionOfParty = (EditText) findViewById(R.id.eventDescription);
//        EditText startTimeOfParty = (EditText) findViewById(R.id.eventStartTime);
//        EditText endTimeOfParty = (EditText) findViewById(R.id.eventEndTime);
//        EditText dateOfParty = (EditText)  findViewById(R.id.eventDate);
//        //EditText buttonCreateParty = findViewById(R.id.createEventButton);
//
//        String location = locationOfParty.getText().toString().trim();
//        String description = descriptionOfParty.getText().toString().trim();
//        String startTime = startTimeOfParty.getText().toString().trim();
//        String endTime = endTimeOfParty.getText().toString().trim();
//        String date = dateOfParty.getText().toString().trim();
//        String dateCreated = new java.util.Date().toString();
//
//        // working on pattern checking for location
//        if (!checkDateString(date)) {
//            Toast.makeText(this, "Put in a valid Date!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!checkStartTime(startTime) || !checkStartTime(endTime)) {
//            Toast.makeText(this, "Put in a valid start time!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        Log.d("Main Activity", "location: "+location);
//
//        if (!TextUtils.isEmpty(location)) {
//            String id = databaseParties.push().getKey();
//
//            Party party = new Party(location, description, startTime, endTime,
//                    date, dateCreated, id);
//
//            databaseParties.child(id).setValue(party);
//
//            Toast.makeText(this, "Party Created", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        else {
//            Toast.makeText(this, "You must set a location", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }
}
