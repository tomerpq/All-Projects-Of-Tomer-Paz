//package com.example.spotparking.Model;
//
//import android.location.Location;
//import android.util.Log;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.GeoPoint;
//
//import java.util.HashMap;
//
//import androidx.annotation.NonNull;
//
///** we will use a lock on the spots so no 2 persons will choose them toghether*/
//public class ParkingSpots {//singleton:
//
//    private static final ParkingSpots ourInstance = new ParkingSpots();
//    public static ParkingSpots getInstance() {
//        return ourInstance;
//    }
//    private ParkingSpots() {}
//    private boolean init = false;
//
//    /* to be used after login*/
//    public void initParkingSpots(){
//        if(!init) {
////            updateUserParkingDataFromFB();
////            updateMarkersFromFB();
//        }
//        init = true;
//    }
//
//    public void initManuallyParkingSpots(){
//        updateUserParkingDataFromFB();
//        updateMarkersFromFB();
//    }
//
//
//    /*important stuff:*/
//    private Markers allMarkersToTimeAvaiable;
//    private UserParkingData mUserParkingData;
//    private FirebaseFirestore mDbOurUsers = FirebaseFirestore.getInstance();
//    private FirebaseFirestore mDbMarkers = FirebaseFirestore.getInstance();
//
//    /** taking care of the Markers information:(shared to all of the users and not per user!) */
//    private void updateMarkersFromFB(){
//        DocumentReference myRef = mDbMarkers.collection("Markers")
//                .document("markers");
//        myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    allMarkersToTimeAvaiable = task.getResult().toObject(Markers.class);
//                    if(allMarkersToTimeAvaiable == null){
//                        allMarkersToTimeAvaiable = new Markers();
//                    }
//                }
//            }
//        });
//    }
//
//    /* will move to google java virtual computer in the future!*/
//    public void updateMarkersToFB(HashMap<Marker, Integer> ourMarkers) {
//        this.allMarkersToTimeAvaiable.setAllMarkersToTimeAvaiable(ourMarkers);
//        DocumentReference myRef = mDbMarkers.collection("Markers")
//                .document("markers");
//        myRef.set(allMarkersToTimeAvaiable).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        });
//    }
//
//    /** taking care of the UserParkingData information: */
//    private void updateUserParkingDataFromFB(){
//        DocumentReference locationRef = mDbOurUsers.collection("UsersParkingData")
//                .document(FirebaseAuth.getInstance().getCurrentUser().
//                        getUid() + " ParkingData:");
//        locationRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    mUserParkingData = task.getResult().toObject(UserParkingData.class);
//                    if(mUserParkingData == null){
//                        mUserParkingData = new UserParkingData();
//                    }
//                }
//            }
//        });
//    }
//
//    /* will move to google java virtual computer in the future!*/
//    public void updateUserParkingDataToFB(){
//        DocumentReference locationRef = mDbOurUsers.collection("UsersParkingData")
//                .document(FirebaseAuth.getInstance().getCurrentUser().
//                        getUid() + " ParkingData:");
//        locationRef.set(mUserParkingData).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        });
//    }
//
//    /** getters and setters:*/
//
//    /*for chaging its values -  will move to google java virtual computer in the future!*/
//    public UserParkingData getmUserParkingData() {
//        return mUserParkingData;
//    }
//
//    public HashMap<Marker, Integer> getOurAllMarkersToTimeAvaiable() {
//        if(allMarkersToTimeAvaiable.getAllMarkersToTimeAvaiable() == null){
//            allMarkersToTimeAvaiable.setAllMarkersToTimeAvaiable(new HashMap<Marker, Integer>());
//        }
//        return allMarkersToTimeAvaiable.getAllMarkersToTimeAvaiable();
//    }
//
//}
