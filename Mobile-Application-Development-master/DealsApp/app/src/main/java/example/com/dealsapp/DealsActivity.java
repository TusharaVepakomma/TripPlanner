package example.com.dealsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DealsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    static ArrayList<Deal> dealArrayList = new ArrayList<>();
    ArrayList<Trip> tripDeals = new ArrayList<>();
    static final ArrayList<String> tripNames = new ArrayList<>();
    CharSequence[] cs;
    public Deal deal = new Deal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
        getSupportActionBar().setTitle("Deals");

        mRecyclerView = (RecyclerView) findViewById(R.id.deals);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("Trips");
                if (dataSnapshot.child("Trips") != null) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        tripDeals.add(dataSnapshot2.getValue(Trip.class));
                        Log.d("demo","TripDeals " +tripDeals.toString());
                    }
                    for (int i = 0; i < tripDeals.size(); i++) {
                        tripNames.add(tripDeals.get(i).getTripName());
                        Log.d("demo","TripDeal Names "+tripDeals.get(i).getTripName().toString());
                    }
                    cs = tripNames.toArray(new CharSequence[tripNames.size()]);
                    Log.d("demo", "Selected "+tripNames );
                    Log.d("demo", "Selected "+cs );
                    Log.d("demo","TripDeals " +tripDeals.toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        CharSequence[] cs = tripNames.toArray(new CharSequence[tripNames.size()]);


        findViewById(R.id.buttonDealsAddToTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < dealArrayList.size(); i++) {
                    if (dealArrayList.get(i).getDealFlag()) {
                        deal = dealArrayList.get(i);
                    }
                    }
                AlertDialog.Builder builder = new AlertDialog.Builder(DealsActivity.this);
                builder.setTitle("Pick a Trip")
                                .setItems(cs, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("demo", "Selected " + cs[which]);
                                        Log.d("demo", "Deal " + deal);
                                        mRootRef.child("Trips").child(String.valueOf(cs[which])).child("TripDeal").setValue(deal);
                                        Intent intent = new Intent(DealsActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("demo", "Clicked ok");

                                    }
                                }).setCancelable(false);

                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.setCancelable(false);

            }


        });

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dealArrayList.clear();
                DataSnapshot dataSnapshot1 = dataSnapshot.child("Deals");
                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                    Deal deal = new Deal();
                    deal.setDealName(dataSnapshot2.child("Place").getValue(String.class));
                    deal.setCost(dataSnapshot2.child("Cost").getValue(Long.class));
                    deal.setDealDuration(dataSnapshot2.child("Duration").getValue(String.class));
                    deal.setLat(dataSnapshot2.child("Location").child("Lat").getValue(Double.class));
                    deal.setLng(dataSnapshot2.child("Location").child("Lon").getValue(Double.class));
                    dealArrayList.add(deal);
                }
                Log.d("Demo", "Deals List " + dealArrayList);
                mAdapter = new DealsAdapter(DealsActivity.this, dealArrayList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
