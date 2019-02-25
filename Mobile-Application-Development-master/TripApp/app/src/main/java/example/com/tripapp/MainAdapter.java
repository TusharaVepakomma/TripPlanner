package example.com.tripapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MONISHA on 04-12-2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewHolder> {

    private ArrayList<Trip> mData;
    private Activity mContext;
    private ArrayList<String> keys;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public MainAdapter(Activity mContext, ArrayList<Trip> mData) {
        this.mData = mData;
        this.mContext = mContext;

    }

    @Override
    public MainAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip, parent, false);
        MainAdapter.RecyclerViewHolder viewHolder = new MainAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.RecyclerViewHolder holder, final int position) {
        holder.textViewTripName.setText(mData.get(position).getTripName());
        holder.textViewTripCity.setText(mData.get(position).getTripCityName());
        holder.imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AddPlaces.class);
                intent.putExtra("tripObj",mData.get(position));
                mContext.startActivity(intent);
            }
        });

        mAdapter = new PlacesAdapter(mContext, mData.get(position).getPlaces(), mData.get(position));
        mRecyclerView.setAdapter(mAdapter);

        holder.imageButtonPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MapsActivity.class);
                intent.putExtra("tripObj",mData.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTripName, textViewTripCity;
        ImageButton imageButtonAdd, imageButtonPlace;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewTripName = (TextView) itemView.findViewById(R.id.textViewTripName);
            textViewTripCity = (TextView) itemView.findViewById(R.id.textViewTripPlace);
            imageButtonAdd = (ImageButton) itemView.findViewById(R.id.imageButtonAddPlace);
            imageButtonPlace = (ImageButton) itemView.findViewById(R.id.imageButtonLocation);

            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.tripsAdded);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(mLayoutManager);

        }
    }
}
