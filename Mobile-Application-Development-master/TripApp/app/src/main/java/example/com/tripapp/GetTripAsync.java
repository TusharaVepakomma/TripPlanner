package example.com.tripapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by MONISHA on 04-12-2017.
 */

public class GetTripAsync extends AsyncTask<StringBuilder, Void, ArrayList<Place>> {
    IData context;


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Place> s) {
        if (s != null) {
            context.returnValue(s);
        } else {
            Log.d("demo", "Empty List");
        }
    }

    @Override
    protected ArrayList<Place> doInBackground(StringBuilder... params) {
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(params[0].toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return TripSearchUtil.TripSearcJSONParser.parseTripSearc(stringBuilder.toString());
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

interface IData {
    void returnValue(ArrayList<Place> str);
}
