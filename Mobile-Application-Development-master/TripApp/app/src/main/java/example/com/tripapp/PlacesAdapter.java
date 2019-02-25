package example.com.tripapp;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MONISHA on 10-12-2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.RecyclerViewHolder> {

    private ArrayList<Place> mData;
    private Activity mContext;
    Trip tripPlace;


    public PlacesAdapter(Activity mContext, ArrayList<Place> mData, Trip tripPlace) {
        this.mData = mData;
        this.mContext = mContext;
        this.tripPlace = tripPlace;
    }

    @Override
    public PlacesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_addplace, parent, false);
        PlacesAdapter.RecyclerViewHolder viewHolder = new PlacesAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesAdapter.RecyclerViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position).getPlaceName());
        Picasso.with(mContext).load(mData.get(position).getIconUrl()).into(holder.imageView);
//        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//                mRootRef.child(tripPlace.getTripName()).child("places").
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageButton imageButtonDelete;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewAddedPlace);
            textView = (TextView) itemView.findViewById(R.id.textViewTripName);
            imageButtonDelete = (ImageButton) itemView.findViewById(R.id.imageButtonDelete);
        }
    }
}
