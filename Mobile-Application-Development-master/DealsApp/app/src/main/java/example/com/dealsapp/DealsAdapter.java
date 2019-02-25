package example.com.dealsapp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MONISHA on 10-12-2017.
 */

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.RecyclerViewHolder> {

    private ArrayList<Deal> mData;
    private Activity mContext;

    public DealsAdapter(Activity mContext, ArrayList<Deal> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public DealsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deals, parent, false);
        DealsAdapter.RecyclerViewHolder viewHolder = new DealsAdapter.RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DealsAdapter.RecyclerViewHolder holder, final int position) {
        holder.placeName.setText(mData.get(position).getDealName());
        holder.duration.setText(mData.get(position).getDealDuration());
        holder.cost.setText(String.valueOf(mData.get(position).getCost()));
        holder.rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mData.get(position).setDealFlag(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView placeName, duration, cost;
        RadioButton rb;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            placeName = (TextView) itemView.findViewById(R.id.textViewDealPlaceName);
            duration = (TextView) itemView.findViewById(R.id.textViewDealDuration);
            cost = (TextView) itemView.findViewById(R.id.textViewDealCostValuePlace);
            rb = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }
}
