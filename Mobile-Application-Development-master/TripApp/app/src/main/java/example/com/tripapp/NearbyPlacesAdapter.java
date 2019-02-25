package example.com.tripapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by MONISHA on 04-12-2017.
 */

public class NearbyPlacesAdapter extends RecyclerView.Adapter<NearbyPlacesAdapter.RecyclerViewHolder> {

    private ArrayList<Place> mData;
    private Activity mContext;
    private Trip trip;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public NearbyPlacesAdapter(Activity mContext, ArrayList<Place> mData, Trip trip) {
        this.mData = mData;
        this.mContext = mContext;
        this.trip = trip;

    }

    @Override
    public NearbyPlacesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nearbyplaces, parent, false);
        NearbyPlacesAdapter.RecyclerViewHolder viewHolder = new NearbyPlacesAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NearbyPlacesAdapter.RecyclerViewHolder holder, final int position) {
        holder.nearbyPlaceName.setText(mData.get(position).getPlaceName());
        Picasso.with(mContext).load(mData.get(position).getIconUrl()).into(holder.placeIcon);
        holder.addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Trip dbTrip = dataSnapshot.child(trip.getTripName()).getValue(Trip.class);
                        dbTrip.getPlaces().add(mData.get(position));
                        Log.d("demo", "List "+dbTrip.getPlaces());
                        dbRef.child(trip.getTripName()).setValue(dbTrip);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mData.remove(position);
                notifyDataSetChanged();
                Intent intent = new Intent(mContext,MainActivity.class);
                mContext.startActivity(intent);
                mContext.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView nearbyPlaceName;
        ImageView addPlace, placeIcon;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nearbyPlaceName = (TextView) itemView.findViewById(R.id.textViewPlaceNearby);
            addPlace = (ImageView) itemView.findViewById(R.id.imageButtonAddNearby);
            placeIcon = (ImageView) itemView.findViewById(R.id.imageViewPlace);

        }
    }
}
