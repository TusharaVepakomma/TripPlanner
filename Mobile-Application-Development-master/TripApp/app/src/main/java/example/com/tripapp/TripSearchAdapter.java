package example.com.tripapp;

import android.app.Activity;
import android.content.Context;
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

/**
 * Created by MONISHA on 04-12-2017.
 */

public class TripSearchAdapter extends RecyclerView.Adapter<TripSearchAdapter.RecyclerViewHolder> {
    public textData data;

    private ArrayList<Place> mData;
    private Activity mContext;

    public TripSearchAdapter(Activity mContext, ArrayList<Place> mData) {
        this.mData = mData;
        this.mContext = mContext;
        this.data = (textData) mContext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_trip_search_item, parent, false);
        TripSearchAdapter.RecyclerViewHolder viewHolder = new TripSearchAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.textViewItem.setText(mData.get(position).getPlaceName());
        holder.place = mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;
        Place place;
//        View v;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textViewItem = (TextView) itemView.findViewById(R.id.searchItem);
//            v = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.tripName(place);
                }
            });
        }
    }
}

interface textData {
    void tripName(Place s);
}

