package example.com.tripapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddTrip extends AppCompatActivity implements IData, textData{

    public EditText editTextTripName;
    public EditText editTextSearchCity;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    public String tripName;
    public  Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getSupportActionBar().setTitle("Add Trip");

        editTextTripName = (EditText) findViewById(R.id.addTripTripName);
        editTextSearchCity = (EditText) findViewById(R.id.addTripsSearchCity);

        mRecyclerView = (RecyclerView) findViewById(R.id.addTripSearchList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        findViewById(R.id.addTripsButtonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedOnline()) {
                    if (editTextSearchCity.getText().toString() != null && !editTextSearchCity.getText().toString().isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyDHt7u_su-Q6yJpIRhSywtNA3nsaXev9Zs&types=(cities)&input=";
                        stringBuilder.append(url);
                        stringBuilder.append(editTextSearchCity.getText().toString());
                        Log.d("demo", stringBuilder.toString());
                        GetTripAsync getTripAsync = new GetTripAsync();
                        getTripAsync.context = AddTrip.this;
                        getTripAsync.execute(stringBuilder);
                    } else {
                        Toast.makeText(AddTrip.this, "Enter City Name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddTrip.this, "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.addTripButtonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip trip = new Trip();
                tripName = editTextTripName.getText().toString();
                trip.setTripName(tripName);
                trip.setTripCityName(place.getPlaceName());
                trip.setTripID(place.getPlaceID());
                Log.d("demo","TripName "+tripName);
                Log.d("demo","SearchCity "+editTextSearchCity.getText().toString());
                mRootRef.child(tripName).setValue(trip);
                Intent intent = new Intent(AddTrip.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isConnectedOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void returnValue(ArrayList<Place> str) {
        mAdapter = new TripSearchAdapter(this, str);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void tripName(Place s) {
        editTextSearchCity.setText(s.getPlaceName().toString());
        Log.d("demo","Place Obj "+s);
        place = s;
    }
}
