package example.com.dealsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Summer Trip");

        findViewById(R.id.buttonGetDeal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DealsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonCreateTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateTripActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonTripList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TripListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonWishList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WishlistActivity.class);
                startActivity(intent);
            }
        });
    }
}
