package nothelloworld.sociallive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    DatabaseReference databaseParties;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        databaseParties = FirebaseDatabase.getInstance().getReference("parties");

        View v = inflater.inflate(R.layout.fragment_createevent, container, false);

        Button createEventButton = (Button) v.findViewById(R.id.createEventButton);
        Button chooseImage = (Button) v.findViewById(R.id.chooseImage);
        createEventButton.setOnClickListener(this);
        chooseImage.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.createEventButton:
                addPartyToDatabase(v);
                break;
            case R.id.chooseImage:
                // TODO: Taylor this is on you!:D
                break;
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

    /**
     * Create a new party and add it to the database.
     * @return true if the user entered valid credentials. If not, then don't.
     */
    private boolean addPartyToDatabase(View v) {

        EditText locationOfParty = (EditText) v.findViewById(R.id.eventLocation);
        EditText descriptionOfParty = (EditText) v.findViewById(R.id.eventDescription);
        EditText startTimeOfParty = (EditText) v.findViewById(R.id.eventStartTime);
        EditText endTimeOfParty = (EditText) v.findViewById(R.id.eventEndTime);
        EditText dateOfParty = (EditText)  v.findViewById(R.id.eventDate);

        String location = locationOfParty.getText().toString().trim();
        String description = descriptionOfParty.getText().toString().trim();
        String startTime = startTimeOfParty.getText().toString().trim();
        String endTime = endTimeOfParty.getText().toString().trim();
        String date = dateOfParty.getText().toString().trim();
        String dateCreated = new java.util.Date().toString();

        // working on pattern checking for location
        if (!checkDateString(date)) {
            Toast.makeText(getActivity(), "Put in a valid Date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkStartTime(startTime) || !checkStartTime(endTime)) {
            Toast.makeText(getActivity(), "Put in a valid start time!", Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.d("Main Activity", "location: "+location);

        if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(description)) {
            String id = databaseParties.push().getKey();

            Party party = new Party(location, description, startTime, endTime,
                    date, dateCreated, id);

            databaseParties.child(id).setValue(party);

            Toast.makeText(getActivity(), "Party Created", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(getActivity(), "You must set a location", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
