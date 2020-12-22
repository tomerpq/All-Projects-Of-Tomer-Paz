package com.example.spotparking.Model;

/**the user who's using the app, and his data:*/
public class User {

    /*can take information from singleton LocalDB*/

    String uid;//user id from register.
    String fbid;//firebase uid in realtime DB.
    //private String displayName;
    private String carManufacturer, carModel, carColor, carNumber;

    private int timeToEvacuate;

    public User(String id, String fbid) {
        this.uid = id;
        this.fbid = fbid;
    }

    public User(){}

//    public User(String id, String fbid, String displayName) {
//        this.uid = id;
//        this.fbid = fbid;
//        this.displayName = displayName;
//
//    }

//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }


    public int getTimeToEvacuate() {
        return timeToEvacuate;
    }

    public void setTimeToEvacuate(int timeToEvacuate) {
        this.timeToEvacuate = timeToEvacuate;
    }

    public String getCarManufacturer() {
        return carManufacturer;
    }

    public void setCarManufacturer(String carManufacture) {
        this.carManufacturer = carManufacture;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getId() {
        return uid;
    }

    public void setId(String id){
        this.uid = id;
    }
    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid){
        this.fbid = fbid;
    }


}