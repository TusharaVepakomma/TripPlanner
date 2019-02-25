package example.com.dealsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TripListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        getSupportActionBar().setTitle("Trip List");
    }
}
