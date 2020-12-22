package com.example.spotparking.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Markers {

    private HashMap<String, Marker> mLocationMarkers; // maps between a uid to a marker.
    private GoogleMap mGoogleMap;
    private Context mContext;
    public Markers(GoogleMap googleMap, Context context) {
        mGoogleMap = googleMap;
        mLocationMarkers = new HashMap<>();
        mContext = context;

    }

    public void hideMarkers(String[] uids){
        for(int i = 0; i < uids.length; i++){
            //mGoogleMap.hide(mLocationMarkers.get(uids[i]));
        }
    }

    public void clear(){
        mGoogleMap.clear();
        mLocationMarkers.clear();
    }
    public void addMarker(String uid,LatLng location, String streetName, double distance, String carDetails) {
        // later change the title to be the string name.
        String unit = "km";
        // change to meters
        if (distance < 1) {
            distance *= 1000;
            unit = "m";

        }
        String snippetText = carDetails + "\n" + String.format("%.1f", distance) + unit;
        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(location).title(streetName).snippet(snippetText));
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
        mLocationMarkers.put(uid, marker);
    }

    public void removeMarker(String uid){
        mLocationMarkers.remove(uid);
    }

    public void removeAllMarkers(){mLocationMarkers.clear();}

    public Marker getMarker(String uid){return mLocationMarkers.get(uid);}


    public void popUpMarkerInfo(String uid){
        mLocationMarkers.get(uid).showInfoWindow();
    }

}
