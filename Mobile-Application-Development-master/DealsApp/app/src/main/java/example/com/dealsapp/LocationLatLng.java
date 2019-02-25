package example.com.dealsapp;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by MONISHA on 10-12-2017.
 */

public class LocationLatLng implements Serializable {
    LatLng latLng;
    Double lat, lng;

    public LatLng getLatLng() {
        return new LatLng(lat,lng);
    }

    public void setLatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
