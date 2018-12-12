package nothelloworld.sociallive;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * TODO: Firebase search parties
 */
public class FindEventFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_findevent, container, false);

        ImageButton boardGameButton = (ImageButton) v.findViewById(R.id.findBoardGames);
        ImageButton videoGameButton = (ImageButton) v.findViewById(R.id.findVideoGames);

        videoGameButton.setOnClickListener(this);
        boardGameButton.setOnClickListener(this);

        return v;
    }

    /**
     * Launch the Board Game Suggestions page
     * @param v
     */
    public void toBoardGameSuggestions(View v) {

    }

    /**
     * Launch the Video Game Suggestion page.
     * @param v
     */
    public void toVideoGameSuggestions(View v) {

    }

    @Override
    public void onClick(View v) {
            switch(v.getId()) {
                case R.id.findBoardGames:
                    Toast.makeText(getActivity(),"BoardGames", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.findVideoGames:
                    Toast.makeText(getActivity(), "VideoGames", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        break;
            }
    }
}
