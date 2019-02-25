package example.com.tripapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MONISHA on 04-12-2017.
 */

public class Trip implements Serializable{
    String tripName, tripCityName, tripID, tripMainLat, tripMainLng;
    ArrayList<Place> places;

    @Override
    public String toString() {
        return "Trip{" +
                "tripName='" + tripName + '\'' +
                ", tripCityName='" + tripCityName + '\'' +
                ", tripID='" + tripID + '\'' +
                ", tripMainLat='" + tripMainLat + '\'' +
                ", tripMainLng='" + tripMainLng + '\'' +
                ", places=" + places +
                '}';
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripCityName() {
        return tripCityName;
    }

    public void setTripCityName(String tripCityName) {
        this.tripCityName = tripCityName;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getTripMainLat() {
        return tripMainLat;
    }

    public void setTripMainLat(String tripMainLat) {
        this.tripMainLat = tripMainLat;
    }

    public String getTripMainLng() {
        return tripMainLng;
    }

    public void setTripMainLng(String tripMainLng) {
        this.tripMainLng = tripMainLng;
    }

    public ArrayList<Place> getPlaces() {
        if(places == null){
            places = new ArrayList<>();
            return places;
        }else{
            return places;
        }
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}

