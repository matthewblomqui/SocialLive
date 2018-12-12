package nothelloworld.sociallive;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * TODO: Pattern checking fixes
 * TODO: Clear form (also button)
 * TODO: Check for duplicates in database
 */
public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference databaseParties;

    // variables for choosing images from your phone
    private static final int PICK_IMAGE_REQUEST = 1;
    //private TextView mTextViewShowUploads;
    //private EditText mEditTextFileName;
    private ImageView mImageView;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    private Button mButtonChooseImage;
    private Button mButtonUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        databaseParties = FirebaseDatabase.getInstance().getReference("parties");
        mStorageRef = FirebaseStorage.getInstance().getReference("parties");

        View v = inflater.inflate(R.layout.fragment_createevent, container, false);

        // these buttons will be needed for choosing images as well
        //Button createEventButton = (Button) v.findViewById(R.id.createEventButton);
        //Button chooseImage = (Button) v.findViewById(R.id.chooseImage);

        mButtonChooseImage = v.findViewById(R.id.chooseImage);
        mButtonUpload = v.findViewById(R.id.createEventButton);

        //createEventButton.setOnClickListener(this);
        //chooseImage.setOnClickListener(this);

        //mTextViewShowUploads = v.findViewsWithText(R.id.);
        mImageView = v.findViewById(R.id.image_view);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in Progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    addPartyToDatabase(v);
                }
            }
        });

        return v;

    }

   @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //case R.id.createEventButton:
              //  addPartyToDatabase(v);
                //break;
           // case R.id.chooseImage:
                // TODO: Taylor this is on you!:D
                   // openFileChooser();
              //  break;
            default:
                break;
        }
    }

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

    // used to get the file extension from the image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /**
     * Create a new party and add it to the database.
     * @return true if the user entered valid credentials. If not, then don't.
     */
    private boolean addPartyToDatabase(View v) {

        EditText locationOfParty = (EditText) getView().findViewById(R.id.eventLocation);
        EditText descriptionOfParty = (EditText) getView().findViewById(R.id.eventDescription);
        EditText startTimeOfParty = (EditText) getView().findViewById(R.id.eventStartTime);
        EditText endTimeOfParty = (EditText) getView().findViewById(R.id.eventEndTime);
        EditText dateOfParty = (EditText)  getView().findViewById(R.id.eventDate);

        final String location = locationOfParty.getText().toString().trim();
        final String description = descriptionOfParty.getText().toString().trim();
        final String startTime = startTimeOfParty.getText().toString().trim();
        final String endTime = endTimeOfParty.getText().toString().trim();
        final String date = dateOfParty.getText().toString().trim();
        final String dateCreated = new java.util.Date().toString();

        //for uploading an image
        if (mImageUri != null) {

        } else {
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }

        // working on pattern checking for location
        //if (!checkDateString(date)) {
          //  Toast.makeText(getActivity(), "Put in a valid Date!", Toast.LENGTH_SHORT).show();
           // return false;
        //}
        //if (!checkStartTime(startTime) || !checkStartTime(endTime)) {
          //  Toast.makeText(getActivity(), "Put in a valid start time!", Toast.LENGTH_SHORT).show();
           // return false;
        //}

        Log.d("Main Activity", "location: "+location);

        if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(description) && mImageUri != null) {
            // for storing the image. This creates a unique id for the image
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

            // check the progress of adding our image into storage
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "Image uploaded", Toast.LENGTH_SHORT).show();

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

                                    Toast.makeText(getActivity(), "Party Created", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(getActivity(), "You must set a location", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void openFileChooser() {
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

            Picasso.with(getActivity()).load(mImageUri).into(mImageView);
            mImageView.setImageURI(mImageUri);
        }
    }
}
