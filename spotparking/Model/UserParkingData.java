package com.example.spotparking.Model;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class UserParkingData {
    private @ServerTimestamp Date timeStamp = null;//timeStamp for help.
    private Marker ourMarker = null;//our parked already marker OR our marker that we go to(selected) OR NULL if none.
    private int finishParkingTime = -1;//time to finish our parking OR time that the other person finishes his OR -1 in case no parking(ours or his selected one).
    private int parkingStatus = 0;//1 in case we parked already, 2 in case we wait for another parking that we selected already, 0 in case no parking at all in both sides.




    public UserParkingData() {

    }


    public Marker getOurMarker() {
        return ourMarker;
    }

    public void setOurMarker(Marker ourMarker) {
        this.ourMarker = ourMarker;
    }

    public int getFinishParkingTime() {
        return finishParkingTime;
    }

    public void setFinishParkingTime(int finishParkingTime) {
        this.finishParkingTime = finishParkingTime;
    }

    public int getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(int parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }



}
