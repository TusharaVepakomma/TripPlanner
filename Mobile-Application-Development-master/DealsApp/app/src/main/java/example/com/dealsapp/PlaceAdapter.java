package example.com.dealsapp;

import android.app.Activity;
import android.location.Location;
import android.support.annotation.FloatRange;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by MONISHA on 10-12-2017.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.RecyclerViewHolder> {

    private ArrayList<Place> mData;
    private Activity mContext;
    double cost;
    sendDist sendDist;

    public PlaceAdapter(Activity mContext, ArrayList<Place> mData) {
        this.mData = mData;
        this.mContext = mContext;
        this.sendDist = (PlaceAdapter.sendDist) mContext;
    }

    @Override
    public PlaceAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_places, parent, false);
        PlaceAdapter.RecyclerViewHolder viewHolder = new PlaceAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PlaceAdapter.RecyclerViewHolder holder, final int position) {
        holder.placeName.setText(mData.get(position).getPlaceName());
        Location locationCharlotte = new Location("Charlotte");
        locationCharlotte.setLatitude(35.307748);
        locationCharlotte.setLongitude(-80.733423);

        Location endPoint = new Location("Final Distance");
        endPoint.setLatitude(mData.get(position).getLat());
        endPoint.setLongitude(mData.get(position).getLng());

        double distance = locationCharlotte.distanceTo(endPoint);
        double distInMiles = (distance / 1000) / 1.6;

        BigDecimal _bdDistance;
        _bdDistance = round((float) distInMiles, 0);
        final String _strDistance = _bdDistance.toString();
        holder.distance.setText(_strDistance + " miles away");

//        holder.checkBox.setChecked(mData.get(position).getFlag());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mData.get(position).setFlag(true);
                    cost = 0.2 * Double.parseDouble(_strDistance);
                    Log.d("demo","Cost "+cost);
                    sendDist.checkField(true);
                    sendDist.sendDistance((float) cost);
                } else{
                    mData.get(position).setFlag(false);
                    cost = 0.2 * Double.parseDouble(_strDistance);
                    sendDist.checkField(false);
                    sendDist.sendDistance((float) cost);
                }
            }
        });
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView placeName, distance, cost;
        EditText editTextPeople;
        CheckBox checkBox;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            placeName = (TextView) itemView.findViewById(R.id.textViewPlaceName);
            distance = (TextView) itemView.findViewById(R.id.textViewDistance);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            editTextPeople = (EditText) itemView.findViewById(R.id.addNumber);
            cost = (TextView) itemView.findViewById(R.id.costResult);
        }
    }

    interface sendDist{
        void sendDistance(Float dist);
        void checkField(Boolean b);
    }
}

