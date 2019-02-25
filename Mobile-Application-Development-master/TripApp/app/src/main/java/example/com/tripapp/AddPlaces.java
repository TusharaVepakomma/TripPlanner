package example.com.tripapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddPlaces extends AppCompatActivity {

    Trip trip = new Trip();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);
        getSupportActionBar().setTitle("Add Places");

        mRecyclerView = (RecyclerView) findViewById(R.id.listViewAddPlaces);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (getIntent().getExtras().containsKey("tripObj")) {
            trip = (Trip) getIntent().getExtras().getSerializable("tripObj");
        }
        StringBuilder stringBuilder = new StringBuilder();
        String url = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyDHt7u_su-Q6yJpIRhSywtNA3nsaXev9Zs&placeid=";
        stringBuilder.append(url);
        stringBuilder.append(trip.getTripID());
        Log.d("demo", "Response 2 "+ stringBuilder.toString());
        new GetPlacesAsync().execute(stringBuilder.toString());
    }

    private class GetPlacesAsync extends AsyncTask<String, Object, ArrayList<Place>> {

        @Override
        protected void onPostExecute(ArrayList<Place> s) {
            if (s != null) {
                mAdapter = new NearbyPlacesAdapter(AddPlaces.this, s, trip);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                Log.d("demo", "Empty List");
            }
        }

        @Override
        protected ArrayList<Place> doInBackground(String... params) {
            BufferedReader bufferedReader = null;
            String lat, lng;

            try {
                URL url = new URL(params[0].toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                JSONObject root = new JSONObject(stringBuilder.toString()).getJSONObject("result").getJSONObject("geometry").getJSONObject("location");
                lat = root.getString("lat");
                lng = root.getString("lng");
                Log.d("demo", "Lat "+lat);
                Log.d("demo", "Long "+ lng);
                trip.setTripMainLat(lat);
                trip.setTripMainLng(lng);

                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildren() != null) {
                            if (dataSnapshot.hasChild(trip.getTripName())) {
                                mRootRef.child(trip.getTripName()).setValue(trip);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                StringBuilder stringBuilder1 = new StringBuilder();
                String url1 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyDHt7u_su-Q6yJpIRhSywtNA3nsaXev9Zs&radius=1000&location=";
                stringBuilder1.append(url1);
                stringBuilder1.append(trip.getTripMainLat() + "," + trip.getTripMainLng());
                Log.d("demo", "Response URL 3 "+ stringBuilder1.toString());
                URL url2 = new URL(stringBuilder1.toString());
                HttpURLConnection connection1 = (HttpURLConnection) url2.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                StringBuilder stringBuilder2 = new StringBuilder();
                String line1 = "";
                while ((line1 = bufferedReader.readLine()) != null) {
                    stringBuilder2.append(line1 + "\n");
                }
                Log.d("demo", "Response 3 "+ stringBuilder2.toString());
                return NearbyPlacesUtil.NearbyPlacesJSONParser.parseNearbyPlaces(stringBuilder2.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null)
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }
    }

}
