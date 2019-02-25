package example.com.dealsapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MONISHA on 10-12-2017.
 */

public class Trip implements Serializable{
    String tripName, tripDistance, numofPpl;
    Long cost;
    ArrayList<Place> placeArrayList;

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(String tripDistance) {
        this.tripDistance = tripDistance;
    }

    public String getNumofPpl() {
        return numofPpl;
    }

    public void setNumofPpl(String numofPpl) {
        this.numofPpl = numofPpl;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public ArrayList<Place> getPlaceArrayList() {
        return placeArrayList;
    }

    public void setPlaceArrayList(ArrayList<Place> placeArrayList) {
        this.placeArrayList = placeArrayList;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripName='" + tripName + '\'' +
                ", tripDistance='" + tripDistance + '\'' +
                ", numofPpl='" + numofPpl + '\'' +
                ", cost=" + cost +
                ", placeArrayList=" + placeArrayList +
                '}';
    }
}
