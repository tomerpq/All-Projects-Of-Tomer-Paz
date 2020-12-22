
package com.example.spotparking.DB;

import android.util.Log;

import com.example.spotparking.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/** our local cache */
public class LocalDB {
    private static final LocalDB ourInstance = new LocalDB();

    public static LocalDB getInstance() {
        return ourInstance;
    }

    private String displayName;

    public int getTimeToEvacuate() {
        return timeToEvacuate;
    }

    public void setTimeToEvacuate(int timeToEvacuate) {
        this.timeToEvacuate = timeToEvacuate;
    }

    private int timeToEvacuate;
    private String fbid;
    private String id;
    private String email, password;
    private String manufactureString = "", modelString = "", colorString = "", numberString = "";
    private ArrayList<String> usersIds;
    private LocalDB() {
        usersIds = new ArrayList<>();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAllDataFromDB(DataSnapshot data) {
        this.fbid = data.getValue(User.class).getFbid();
        this.id = data.getValue(User.class).getId();
        this.manufactureString = data.getValue(User.class).getCarManufacturer();
        this.modelString = data.getValue(User.class).getCarModel();
        this.colorString = data.getValue(User.class).getCarColor();
        this.numberString = data.getValue(User.class).getCarNumber();
        this.timeToEvacuate = data.getValue(User.class).getTimeToEvacuate();
        this.displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    public User createUserFromDb() {
        // check that id's are not null
        if (this.id == null || this.fbid == null) return null;
        User user = new User(this.id, this.fbid);
        user.setCarColor(this.colorString);
        user.setCarManufacturer(this.manufactureString);
        user.setCarModel(this.modelString);
        user.setCarNumber(this.numberString);
        return user;
    }

    public void addUserId(String uid){
        usersIds.add(uid);
        Log.d("local", "addUserId: " + uid);
    }

    public ArrayList<String> getUsersIdsList() {
        return usersIds;
    }

    public String getId() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getModelString() {
        return modelString;
    }

    public void setModelString(String modelString) {
        this.modelString = modelString;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public String getNumberString() {
        return numberString;
    }

    public void setNumberString(String numberString) {
        this.numberString = numberString;
    }


    public String getManufactureString() {
        return manufactureString;
    }

    public void setManufactureString(String manufactureString) {
        this.manufactureString = manufactureString;
    }

    public String getFbid() {
        return fbid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }
}