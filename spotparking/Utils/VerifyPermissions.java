package com.example.spotparking.Utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.R;
import com.example.spotparking.UI.FindParkingActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.spotparking.Constants.ERROR_DIALOG;
import static com.example.spotparking.Constants.PERMISSION_REQUEST_ENABLE_FINE_LOCATION;
import static com.example.spotparking.Constants.PERMISSION_REQUEST_ENABLE_GPS;

public class VerifyPermissions extends AppCompatActivity {

    private boolean mLocationPermissionGranted = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean checkMapServiced(){
        if (isGoogleServicesActive()) {
            return isGpsEnabled();
        }
        return false;
    }

    /*
    Check that the user has google services active, and if not open dialog
    to solve the problem.
     */
    public boolean isGoogleServicesActive() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);

        // google services are active - all good.
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(isAvailable)){
            googleApiAvailability.getErrorDialog(this, isAvailable, ERROR_DIALOG);
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.no_google_services_error),
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void startAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.require_gps))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSION_REQUEST_ENABLE_GPS);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /*
    checks that the user enabled the gps permission.
    if not, deal with it with an alert.
     */
    public boolean isGpsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // used for debugging
        assert manager != null : "Provider Disabled";
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            startAlertMessageNoGps();
            return false;
        }
        return true;
    }


    /*
    after the user answers the alert dialog about the gps,
    if he enabled the gps, start the map activity
    else, get the location permission - explicitly ask for the permissions.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    startMapActivity();
                } else {
                    getLocationPermission();
                }
            }
        }
    }

    /*
    checks if the user has enabled location permission, and if he does'nt,
    explicitly request the user to enable.
     */
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
            startMapActivity();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_ENABLE_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSION_REQUEST_ENABLE_FINE_LOCATION : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    mLocationPermissionGranted = true;
            }
        }
    }

    /*
    Runs when the user starts seeing the app.
    checks if all the permissions are good, if they are, then launch map activity.
    else , explicitly ask them to activate.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServiced()) {
            if (mLocationPermissionGranted) {
                startMapActivity();
            }else
                getLocationPermission();
        }
    }
    private void startMapActivity(){
        Intent myIntent = new Intent(getBaseContext(), FindParkingActivity.class);//verifyPermisiion activity start
        finish();
        startActivity(myIntent);
    }

}
