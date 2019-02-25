package example.com.tripapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MONISHA on 04-12-2017.
 */

public class NearbyPlacesUtil {
    static public class NearbyPlacesJSONParser {
        static ArrayList<Place> parseNearbyPlaces(String in) throws JSONException {
            ArrayList<Place> nearbyPlacesArrayList = new ArrayList<Place>();
//            try {
            JSONObject root = new JSONObject(in);
            JSONArray usersJSONArray = root.getJSONArray("results");
            for (int i = 0; i < usersJSONArray.length(); i++) {
                Place place = new Place();
                JSONObject usersJSONObject = usersJSONArray.getJSONObject(i);
                JSONObject objGeo = usersJSONObject.getJSONObject("geometry").getJSONObject("location");
                place.setLat(objGeo.getString("lat"));
                place.setLng(objGeo.getString("lng"));
                place.setIconUrl(usersJSONObject.getString("icon"));
                place.setPlaceName(usersJSONObject.getString("name"));
                String id = usersJSONObject.getString("place_id");
                place.setPlaceID(id);
                nearbyPlacesArrayList.add(place);
            }

            Log.d("demo", nearbyPlacesArrayList.toString());
            return nearbyPlacesArrayList;
        }
    }
}
