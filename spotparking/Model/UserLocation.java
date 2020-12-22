package com.example.spotparking.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserLocation {

    private GeoPoint mGeoPoint;
    private @ServerTimestamp Date mTimeStamp;
    private User mUser;
    public UserLocation() {

    }

        public UserLocation(GeoPoint geoPoint, Date timeStamp, User user) {
        mGeoPoint = geoPoint;
        mTimeStamp = timeStamp;
        mUser = user;
    }

    public GeoPoint getGeoPoint() {
        return mGeoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        mGeoPoint = geoPoint;
    }

    public Date getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        mTimeStamp = timeStamp;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserLocation{" +
                "mGeoPoint=" + mGeoPoint +
                ", mTimeStamp='" + mTimeStamp + '\'' +
                ", mUser=" + mUser +
                '}';
    }


}
