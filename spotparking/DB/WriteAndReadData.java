package com.example.spotparking.DB;

import android.util.Log;

import com.example.spotparking.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class WriteAndReadData {

    public void saveDetails(String manufactureString, String modelString, String colorString, String numberString, int timeToEvacuate) {
        if (colorString == null) {
            colorString = "";
        }
        if (numberString == null) {
            numberString = "";
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OurUsers");
        myRef.child(uid).child("carManufacturer").setValue(manufactureString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        myRef.child(uid).child("carModel").setValue(modelString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        myRef.child(uid).child("carColor").setValue(colorString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        myRef.child(uid).child("timeToEvacuate").setValue(timeToEvacuate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        myRef.child(uid).child("carNumber").setValue(numberString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        Log.d("check", "saveDetails: here");
        myRef.child(uid).child("Display Name").setValue(user.getDisplayName());
//        //saving last values to the cache:
//        LocalDB localDB = LocalDB.getInstance();
//        localDB.setManufactureString(manufactureString);
//        localDB.setModelString(modelString);
//        localDB.setColorString(colorString);
//        localDB.setNumberString(numberString);
    }

    public void saveCarManufacture(String manufactureString) {
        String currentUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OurUsers");
        myRef.child(currentUid).child("carManufacture").setValue(manufactureString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //saving last value to the cache:
        LocalDB.getInstance().setManufactureString(manufactureString);
    }

    public void saveCarModel(String modelString) {
        String currentUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OurUsers");
        myRef.child(currentUid).child("carModel").setValue(modelString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //saving last value to the cache:
        LocalDB.getInstance().setModelString(modelString);
    }

    public void saveCarColor(String colorString) {
        if (colorString == null) {
            colorString = "";
        }
        String currentUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OurUsers");
        myRef.child(currentUid).child("carColor").setValue(colorString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //saving last value to the cache:
        LocalDB.getInstance().setColorString(colorString);
    }

    public void saveCarNumber(String numberString) {
        if (numberString == null) {
            numberString = "";
        }
        String currentUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OurUsers");
        myRef.child(currentUid).child("carNumber").setValue(numberString).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //saving last value to the cache:
        LocalDB.getInstance().setNumberString(numberString);
    }

    public void readCurrentCarDetails() {
        final String currentUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OurUsers");
        //Read it from the database:
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get current userID:
                LocalDB localDB = LocalDB.getInstance();
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    String id = userDetails.getValue(User.class).getId();
                    if (id == null) continue;
                    if (id.equals(currentUid)) {
                        User user = userDetails.getValue(User.class);
                        localDB.setManufactureString(user.getCarManufacturer());
                        localDB.setModelString(user.getCarModel());
                        localDB.setColorString(user.getCarColor());
                        localDB.setNumberString(user.getCarNumber());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}