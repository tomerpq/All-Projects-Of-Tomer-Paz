/*
package com.example.spotparking;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlaceApi {
    public ArrayList<String> AutoComplete(String input) {
        ArrayList<String> arrayList = new ArrayList<String>();
        HttpURLConnection connection = null;
        StringBuilder jsonResult = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            sb.append("input=" + input).append("&key=AIzaSyBO8RqMuI3ysuXgHp_boa5DOqzY2yvc_wU");
            URL url = new URL(sb.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = inputStreamReader.read(buff)) != -1){
                jsonResult.append(buff, 0, read);
            }
            Log.d("jsonresults", "AutoComplete: " + jsonResult);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonResult.toString());
            JSONArray predictions = jsonObject.getJSONArray("predictions");
            Log.d("auto", "AutoComplete: " + predictions);
            for (int i = 0; i < predictions.length(); i++) {
                arrayList.add(predictions.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("arraylist", "AutoComplete: " + arrayList);
        return arrayList;
    }
}
*/
