package nothelloworld.sociallive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

// This will pass the data from our HomeFragment to the Party Class
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private Context mContext;
    private List<Party> mParties;

    public ImageAdapter(Context context, List<Party> parties) {
        mContext = context;
        mParties = parties;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    // Get data out of out party items and into our recycler view items
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int position) {
        Party partyCurrent = mParties.get(position);
        imageViewHolder.textViewName.setText(partyCurrent.getTitle());
        Picasso.with(mContext)
                .load(partyCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageView);
    }

    @Override
    // determines how many parties we will show to the user
    public int getItemCount() {
        return mParties.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}


