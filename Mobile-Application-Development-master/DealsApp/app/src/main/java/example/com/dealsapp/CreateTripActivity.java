package example.com.dealsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateTripActivity extends AppCompatActivity implements PlaceAdapter.sendDist {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    static ArrayList<Place> placeArrayList = new ArrayList<>();
    public EditText editTextTripName, editTextNumberofPeople;
    public String tripName, numberofPeople;
    double j = 0;
    TextView cost;
    Boolean check = false;
    ArrayList<Place> places = new ArrayList<>();
    Trip trip = new Trip();
    ArrayList<LatLng> locations = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        getSupportActionBar().setTitle("Create Trip");

        editTextTripName = (EditText) findViewById(R.id.addTripName);
        editTextNumberofPeople = (EditText) findViewById(R.id.addNumber);
        cost = (TextView) findViewById(R.id.costResult);
        numberofPeople = editTextNumberofPeople.getText().toString();

        findViewById(R.id.buttonAddTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTripName.getText().toString() != null && !editTextTripName.getText().toString().isEmpty()
                        && editTextNumberofPeople.getText().toString() != null && !editTextNumberofPeople.getText().toString().isEmpty()) {
                    for (int i = 0; i < placeArrayList.size(); i++) {
                        if (placeArrayList.get(i).getFlag()) {
                            places.add(placeArrayList.get(i));
                        }
                    }
                    trip.setTripName(editTextTripName.getText().toString());
                    trip.setNumofPpl(editTextNumberofPeople.getText().toString());
                    trip.setPlaceArrayList(places);
                    mRootRef.child("Trips").child(trip.getTripName()).setValue(trip);
                    Intent intent = new Intent(CreateTripActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateTripActivity.this, "Enter the field values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.places);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                placeArrayList.clear();
                DataSnapshot dataSnapshot1 = dataSnapshot.child("Places");
                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                    Place place = new Place();
                    place.setPlaceName(dataSnapshot2.child("City").getValue(String.class));
                    place.setLat(dataSnapshot2.child("Location").child("Lat").getValue(Double.class));
                    place.setLng(dataSnapshot2.child("Location").child("Lon").getValue(Double.class));
                    placeArrayList.add(place);
                }
                Log.d("Demo", "Deals List " + placeArrayList);
                mAdapter = new PlaceAdapter(CreateTripActivity.this, placeArrayList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.buttonViewTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < placeArrayList.size(); i++) {
                    if (placeArrayList.get(i).getFlag()) {
                        places.add(placeArrayList.get(i));
                        LocationLatLng locationLatLng = new LocationLatLng();
                        locationLatLng.setLatLng(places.get(i).getLat(),places.get(i).getLng());
                        locations.add(locationLatLng.getLatLng());
                    }
                    Log.d("demo","Location "+locations);
                    Intent intent = new Intent(CreateTripActivity.this,MapsActivity.class);
                    intent.putExtra("locations", locations);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void sendDistance(Float dist) {
        if (check) {
            Float costVal = dist * Integer.parseInt(editTextNumberofPeople.getText().toString());
            j = j + costVal;
            cost.setText(String.valueOf(j));
        } else {
            Float costVal = dist * Integer.parseInt(editTextNumberofPeople.getText().toString());
            j = j - costVal;
            cost.setText(String.valueOf(j));
        }
    }

    @Override
    public void checkField(Boolean b) {
        check = b;
    }
}
