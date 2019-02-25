package example.com.tripapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MONISHA on 04-12-2017.
 */

public class TripSearchUtil {
    static public class TripSearcJSONParser {
        static ArrayList<Place> parseTripSearc(String in) throws JSONException {
            ArrayList<Place> tripSearcArrayList = new ArrayList<Place>();
//            try {
            JSONObject root = new JSONObject(in);
            JSONArray usersJSONArray = root.getJSONArray("predictions");
            for (int i = 0; i < usersJSONArray.length(); i++) {
                Place place = new Place();
                JSONObject usersJSONObject = usersJSONArray.getJSONObject(i);
                String recipes1 = usersJSONObject.getString("description");
                int a = recipes1.lastIndexOf(",");
                recipes1.substring(0,a);
                place.setPlaceName(recipes1.substring(0,a));
                String id = usersJSONObject.getString("place_id");
                place.setPlaceID(id);
                tripSearcArrayList.add(place);
            }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            Log.d("demo",tripSearcArrayList.toString());
            return tripSearcArrayList;
        }
    }
}
