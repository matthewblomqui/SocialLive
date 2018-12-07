package nothelloworld.sociallive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventFragment extends Fragment {

    DatabaseReference databaseParties;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        databaseParties = FirebaseDatabase.getInstance().getReference("parties");

        View v = inflater.inflate(R.layout.fragment_createevent, container, false);

        return v;

    }
}
