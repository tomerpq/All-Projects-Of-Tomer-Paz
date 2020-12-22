package com.example.spotparking.UI;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.spotparking.Algorithm.AlgorithmNaiveGetInOut;
import com.example.spotparking.Algorithm.IAlgorithm;
import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.Model.Markers;
import com.example.spotparking.Model.PolylineData;
import com.example.spotparking.Model.User;
import com.example.spotparking.Model.UserLocation;
import com.example.spotparking.R;

import com.example.spotparking.databinding.ActivityMapBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.spotparking.Settings.MapSettingsActivity;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import static com.example.spotparking.Constants.DEFAULT_COORDINATES;
import static com.example.spotparking.Constants.DEFAULT_ZOOM;
import static com.example.spotparking.Constants.FOCUS_ON_SPOT_ZOOM;
import static com.example.spotparking.Constants.MAPVIEW_BUNDLE_KEY;


public class MapActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback,
        SharedPreferences.OnSharedPreferenceChangeListener, GoogleMap.OnPolylineClickListener {

    private static final String TAG = "MapActivity";
    private ActivityMapBinding binding;
    private FirebaseFirestore mDb;
    private Button refresh;
    private Button algoStart;
    private HomeWatcher mHomeWatcher;
    private IAlgorithm algorithm;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private UserLocation mCurrentUserLocation;
    private Markers mMarkers;
    private ArrayList<UserLocation> mUsersLocations; // not including current user
    private HashMap<Integer, Double> mDistancesToUsers; // maps between the index in the mUsersLocations list to the distance
    private PlacesClient mPlacesClient;
    private GeoApiContext mGeoApiContext = null;
    private ArrayList<PolylineData> mPolyLinesData = new ArrayList<>();
    private Context context;


    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initHomeWatcher();
        refresh = (Button) findViewById(R.id.refreshButton);
        algoStart = (Button) findViewById(R.id.algoStartButton);
        mDb = FirebaseFirestore.getInstance();
        initMap(savedInstanceState);
        Toolbar mToolBar = findViewById(R.id.tool_bar_map);
        setSupportActionBar(mToolBar);
        //make algorithm object
        algorithm = new AlgorithmNaiveGetInOut();
        // floating button to go to the Big button .
        binding.findParkingFloatingBtn.setOnClickListener(view -> {
            Intent myIntent = new Intent(getApplicationContext(), FindParkingActivity.class);//verifyPermisiion activity start
            startActivity(myIntent);
            finish();
        });
        Places.initialize(getApplicationContext(), "AIzaSyC3ukz_zStw9uEv3WH143305j75moB4EWs");
        mUsersLocations = new ArrayList<>();
        mDistancesToUsers = new HashMap<>();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        context = this;
    }


    private void initHomeWatcher() {
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                ////     SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
                //    SharedPreferences.Editor editor = prefs.edit();
                //     editor.putString("lastActivity", getClass().getName());
                //    editor.commit();
                startService(new Intent(MapActivity.this, FloatingWidgetShowService.class));
                //finish();
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();

    }

    private void initZoomBar() {
        SeekBar zoomBar = findViewById(R.id.zoom_sk);
        zoomBar.setMax((int) mGoogleMap.getMaxZoomLevel() - 3);
        zoomBar.setProgress((int) DEFAULT_ZOOM);
        zoomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i < 7) i = 7;
                zoomCamera(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    private void refreshMarkers(){
        mMarkers.clear();
        updateOtherUsersLocations(false);
    }

    private void refreshforMyMarker(){
        mMarkers.clear();
        updateOtherUsersLocations(true);
    }

    /**
     * Here we can manipulate the map once its available - change camera, location, map type etc.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        saveFBIDToLocalDB();
        mGoogleMap = map;
        mGoogleMap.setOnPolylineClickListener(this);
        mMarkers = new Markers(mGoogleMap, getApplicationContext());
        updateLocationUI();
        getUserDetails(false);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshMarkers();
            }
        });
        if (mCurrentUserLocation == null) {
            Toast.makeText(MapActivity.this, "Error! Couldnt load user details. going back to login", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
        updateOtherUsersLocations(true);
        setUpSharedPreferences();
        setMyLocationButtonPos();
        /*
        init widgets on the map
         */
        initSearchBar();
        initZoomBar();
        mGoogleMap.setOnInfoWindowClickListener(this);
        algoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMarkers.clear();
                mUsersLocations.clear();
                mDistancesToUsers.clear();
                GeoPoint geoPointParked = null;
                while(true){
                    Log.e(TAG, "onClick: parked1: " );
                    algorithm.setSpeed(getGpsSpeed());
                    boolean parked = algorithm.algorithm(true);
                    Log.e(TAG, "GetInPark\nspeed = " + algorithm.getSpeed() +
                            " gotToHighSpeed = " + algorithm.getGotToHighSpeed()
                            + " timeCounter = " + algorithm.getTimeCounter());
                    if(parked){
                        saveTimeToEvacuate(1000);
                        getLastKnownLocation(false);
                        geoPointParked = mCurrentUserLocation.getGeoPoint();
                        refreshforMyMarker();
                        break;
                    }
                    Log.e(TAG, "onClick: parked1: " + parked);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //2 minuted to let us get away from the park
                try {
                    Thread.sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "2 minutes done. Starting GetOutParking Detection");
                while(true){
                    algorithm.setDistanceToPark(getDistanceToPark(geoPointParked));
                    boolean gotToPark = algorithm.algorithm(false);
                    Log.e(TAG, "GetOutPark\nDistanceToParking: " + algorithm.getDistanceToPark() +
                            " TimeToEvacuate: " + algorithm.getTimeToEvacuate());
                    int time = algorithm.getTimeToEvacuate();
                    saveTimeToEvacuate(time);
                    if(gotToPark){
                        Log.e(TAG, "gotToParking NOW");
                        break;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * focus the camera on the best parking spot, and pop up the marker info of that spot.
     * for now we just go to the closest spot using aerial distance spot, later the algorithm should find the best one.
     */
    private void focusOnSpot() {
        Log.d(TAG, "focusOnSpot: " + mUsersLocations.size());
        if (mUsersLocations.size() == 0) return;
        Map.Entry best = null;
        for (Map.Entry element : mDistancesToUsers.entrySet()) {
            if (best == null)
                best = element;
            double distance = (double) element.getValue();
            if (distance < (double) best.getValue())
                best = element;

        }
        UserLocation userLocation = mUsersLocations.get((int) best.getKey());
        GeoPoint location = userLocation.getGeoPoint();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        moveCamera(latLng, FOCUS_ON_SPOT_ZOOM);
        mMarkers.popUpMarkerInfo(userLocation.getUser().getId());
    }

    /**
     * calculate the distance in KM from geo point A to geo point B
     *
     * @param pointA first point
     * @param pointB second point
     * @return the distance in KM
     */
    private static double geoPointsDistance(@NonNull GeoPoint pointA, @NonNull GeoPoint pointB) {
        double lon1 = Math.toRadians(pointA.getLongitude());
        double lon2 = Math.toRadians(pointB.getLongitude());
        double lat1 = Math.toRadians(pointA.getLatitude());
        double lat2 = Math.toRadians(pointB.getLatitude());
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }


    private String getStreetName(GeoPoint geoPoint) {
        try {
            List<Address> addresses;
            Geocoder geocoder = new Geocoder(this);
            addresses = geocoder.getFromLocation(geoPoint.getLatitude(), geoPoint.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                String street = addresses.get(0).getAddressLine(0);
                //String city = addresses.get(0).getAdminArea();
                return street;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addMarker(UserLocation userLocation) {
        Log.d(TAG, "updateMarkers: size = " + mUsersLocations.size());
        double distance = 0;

        Log.d(TAG, "updateMarkers: user: " + userLocation.getUser().getId());
        String streetName = getStreetName(userLocation.getGeoPoint());
        if (streetName == null) {
            streetName = "Unknown Location";
        }
        try {
            distance = mDistancesToUsers.get(mDistancesToUsers.size() - 1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        String id = userLocation.getUser().getId();
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(getString(R.string.our_users_ref));
        double finalDistance = distance;
        String finalStreetName = streetName;
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data == null) continue;
                    String uid = data.getValue(User.class).getId();
                    if (uid == null) {
                        Log.d("Markers", "onDataChange: Error with this data: " + data);
                        continue;
                    }
                    if (id.equals(uid)) {
                        String carColor = data.child("carColor").getValue(String.class);
                        String carManufacturer = data.child("carManufacturer").getValue(String.class);
                        int timeToEvacuate = data.child("timeToEvacuate").getValue(Integer.class);
                        String carDetails = carColor + " " + carManufacturer + "\nEvacuate in: "
                                + timeToEvacuate + " Minutes";
                        GeoPoint geoPoint = userLocation.getGeoPoint();
                        mMarkers.addMarker(id,
                                new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()), finalStreetName, finalDistance, carDetails);
                        Log.d(TAG, "updateMarkers: added marker at " + geoPoint.getLatitude() + ", " + geoPoint.getLongitude());

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    /**
     * updated the mUsersLocations lists from the firestore.
     * we will need to do that from the background service too.
     */
    private void updateOtherUsersLocations(boolean onlyMine) {
        Log.d(TAG, "here");
        final ArrayList<String> usersIds = LocalDB.getInstance().getUsersIdsList();
        final int[] count = {0};
        mUsersLocations.clear();
        mDistancesToUsers.clear();
        for (String uid : usersIds) {
            Log.d(TAG, "onComplete:  user " + uid);
            DocumentReference locationsRef = mDb.collection(getString(R.string
                    .collection_user_location))
                    .document(uid);
            locationsRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    count[0]++;
                    Log.d(TAG, "count" + count[0]);
                    UserLocation userLocation = task.getResult().toObject(UserLocation.class);
                    // if we succeeded getting the user location, and the user is different
                    // then current user, so we dont add the current user to the list
                    try {
                        if (!onlyMine && userLocation != null && !userLocation.getUser().getId().equals(mCurrentUserLocation.getUser().getId())) {
                            mUsersLocations.add(userLocation);
                            Log.d(TAG, "onComplete: added user " + userLocation.getUser().getId());

                            // A null location will make a system crash
                            if (userLocation.getGeoPoint() == null || mCurrentUserLocation.getGeoPoint() == null)
                                return;
                            // calculate the distance from my location to that user location.
                            double distance = geoPointsDistance(mCurrentUserLocation.getGeoPoint(),
                                    userLocation.getGeoPoint());
                            // update the distances hash map with the userLocation index and his distance.
                            mDistancesToUsers.put(mUsersLocations.size() - 1, distance);
                            // update the map markers.
                            addMarker(userLocation);

                            if (count[0] == usersIds.size()) {
                                // if we got here from the Big Search Button, start animating the search.
                                // we do this here to make sure the user location is retrieved from the firestore.
                                if (getIntent().hasExtra("start_searching")) {
                                    Toast.makeText(MapActivity.this, "Searching...", Toast.LENGTH_LONG).show();
                                    focusOnSpot();
                                }
                            }
                            // user location is null or equals to current user
                        }
                        else if(onlyMine && userLocation != null && userLocation.getUser().getId().equals(mCurrentUserLocation.getUser().getId())){
                            addMarker(userLocation);
                        }
                        else {
                            Log.d(TAG, "onDataChange: null or current user");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


    /**
     * init the search bar.
     */
    private void initSearchBar() {
        Log.d(TAG, "initSearchBar: ");
        // handle the key events for searching
        binding.inputSearchEt.setFocusable(false);
        binding.inputSearchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                        .build(MapActivity.this);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            binding.inputSearchEt.setText(place.getAddress());
            // search the location
            geoLocateLocation();
//                    // hide the soft keyboard after done using it.
            hideSoftKeyboard();
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * gets the address from the search bar, and moves the camera to that location
     */
    private void geoLocateLocation() {
        String searchString = binding.inputSearchEt.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.d(TAG, "geoLocateLocation: " + e.getMessage());
        }
        if (addressList.size() > 0) {
            Address address = addressList.get(0);

            // moves the camera to that position.
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM);
        }
    }


    /**
     * starts the map.
     */
    private void initMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }
    }


    private void calculateDirections(Marker marker) {
        Log.d(TAG, "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(
                        mCurrentUserLocation.getGeoPoint().getLatitude(),
                        mCurrentUserLocation.getGeoPoint().getLongitude()
                )
        );
        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());

                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage());

            }
        });
    }

    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);
                if(mPolyLinesData.size() > 0){
                    for(PolylineData polylineData: mPolyLinesData){
                        polylineData.getPolyline().remove();
                    }
                    mPolyLinesData.clear();
                    mPolyLinesData = new ArrayList<>();
                }

                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mGoogleMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(context, R.color.quantum_grey500));
                    polyline.setClickable(true);
                    mPolyLinesData.add(new PolylineData(polyline, route.legs[0]));
                }
            }
        });
    }


    /**
     * Turn on the My Location layer and the related control on the map.
     */
    private void updateLocationUI() {
        if (mGoogleMap == null) return;
        try {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            Log.e("Security Exception: ", e.getMessage());
        }
    }


    private void setUpSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setMapType(sharedPreferences);
        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.map_type_key))) {
            setMapType(sharedPreferences);
        }
    }

    /**
     * sets the map type(normal,hybrid,satellite) according to the shared prefs.
     */
    private void setMapType(SharedPreferences prefs) {
        mGoogleMap.setMapType(Integer.parseInt(prefs.getString(getString(R.string.map_type_key),
                getString(R.string.map_type_normal_value))));
    }

    /**
     * gets the current user details from the localDb
     */
    private void getUserDetails(boolean withoutSave) {
        if (mCurrentUserLocation == null) {
            User user = LocalDB.getInstance().createUserFromDb();
            if (user == null) {
                Log.d(TAG, "getUserDetails: user is null");
                return;
            }
            mCurrentUserLocation = new UserLocation();
            mCurrentUserLocation.setUser(user);
            getLastKnownLocation(withoutSave);
        }
    }


    /**
     * gets the last known location, and places the camera there.
     * also calls the SaveUserLocation method.
     */
    private void getLastKnownLocation(boolean withoutSave) {
        try {
            Log.e(TAG, "Entered - getLastKnownLocation: " );
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Log.e(TAG, "onBeforeComplete:");
                            // if the task succeeded, then we got the location of the user and set the
                            // camera to that position
                            if (task.isSuccessful()) {
                                // Set the map's camera position to the current location of the device.
                                Location location = task.getResult();
                                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                                Log.e(TAG, "onComplete: last = " +geoPoint.getLatitude() + " " + geoPoint.getLongitude());
                                moveCamera(new LatLng(geoPoint.getLatitude(),
                                        geoPoint.getLongitude()), DEFAULT_ZOOM);

                                mCurrentUserLocation.setGeoPoint(geoPoint);
                                mCurrentUserLocation.setTimeStamp(null);
                                if(!withoutSave)
                                    saveUserLocation();
                                Log.d(TAG, "my location is: " + geoPoint.getLatitude() + ", " + geoPoint.getLongitude());

                                // couldn't get the user's location, so we set the default location
                            } else {
                                Log.d("default", "Current location is null. Using defaults.");
                                Log.e(TAG, "Exception: %s", task.getException());
                                moveCamera(DEFAULT_COORDINATES,
                                        DEFAULT_ZOOM);
                                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                            }
                        }
                    });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * save the user location to the firestore collection of UserLocation
     */
    private void saveUserLocation() {
        if (mCurrentUserLocation != null) {
            Log.d(TAG, "saveUserLocation: ");
            DocumentReference ref = mDb.collection(getString(R.string.collection_user_location))
                    .document(FirebaseAuth.getInstance().getUid());
            ref.set(mCurrentUserLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: saved");
                    }
                }
            });
        }
    }


    /**
     * sets the my location button pos to bottom right corner
     */
    private void setMyLocationButtonPos() {
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 60);
    }

    /**
     * moves the camera to the latlng position and sets the zoom level.
     */
    private void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void zoomCamera(int zoom) {
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }


    /**
     * hides the soft keyboard after we done using it - lost focus.
     */
    private void hideSoftKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * Methods for setting up the menu
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our map_menu layout to this menu */
        inflater.inflate(R.menu.map_menu, menu);
        /* Return true so that the map_menu is displayed in the Toolbar */
        return true;
    }


    /**
     * handles menu selection.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // if the user choose the settings, start the settings activity
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MapSettingsActivity.class));
            return true;
        }
        if (id == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Navigate to this parking spot?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        calculateDirections(marker);
                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * asks the user if he really wants to exit the app by popping a dialog.
     */
    private void startExitDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.exit_dialog))
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * when back is pressed, start an exit dialog
     */
    // @Override
    // public void onBackPressed() {
    //      startExitDialog();

    //   }
    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // update when resuming.
        getUserDetails(true);
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
        mHomeWatcher.stopWatch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    //for km/hour
    private double speedMeterInSecondToKmh(long speed)
    {
        double kmhSpeed = (double)(3.6 * (speed));
        return kmhSpeed;
    }

    private long calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        long distanceInMeters = Math.round(6371000 * c);
        return distanceInMeters;
    }

    private GeoPoint getCurrentGeoPoint(){
        GeoPoint[] geoPoint = {null};
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            // if the task succeeded, then we got the location of the user and set the
                            // camera to that position
                            if (task.isSuccessful()) {
                                // Set the map's camera position to the current location of the device.
                                Location location = task.getResult();
                                geoPoint[0] = new GeoPoint(location.getLatitude(), location.getLongitude());
                                Log.e(TAG, "onCompleteeeee: "+geoPoint[0] );
                            }
                        }

                    });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
        return geoPoint[0];
    }

    private double getDistanceToPark(GeoPoint from){
        getLastKnownLocation(true);
        GeoPoint currentGeo = mCurrentUserLocation.getGeoPoint();
        double distance = (double)calculateDistance(currentGeo.getLatitude(),currentGeo.getLongitude(),from.getLatitude(),from.getLongitude());
        return distance;
    }

    private double getGpsSpeed(){
        getLastKnownLocation(true);
        GeoPoint geoPoint1 = mCurrentUserLocation.getGeoPoint();
        Log.e(TAG, "getGpsSpeed: UserLocation Start = " + mCurrentUserLocation );
        double lat1 = geoPoint1.getLatitude();
        double lon1 = geoPoint1.getLongitude();
        Log.e(TAG, "getGpsSpeed: " + " lat1 = " + lat1 + " lon1 = " + lon1);
        double[] lat2 = {0.0};
        double[] lon2 = {0.0};
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getLastKnownLocation(true);
        GeoPoint geoPoint2 = mCurrentUserLocation.getGeoPoint();
        Log.e(TAG, "getGpsSpeed: UserLocation End = " + mCurrentUserLocation );
        lat2[0] = geoPoint2.getLatitude();
        lon2[0] = geoPoint2.getLongitude();
        Log.e(TAG, "getGpsSpeed: " + " lat2 = " + lat2[0] + " lon2 = " + lon2[0]);
        long distInMetersAndSpeedMetersInSecond = calculateDistance(lat1,lon1,lat2[0],lon2[0]);
        return speedMeterInSecondToKmh(distInMetersAndSpeedMetersInSecond);
    }

    private void saveTimeToEvacuate(int time){
        mCurrentUserLocation.getUser().setTimeToEvacuate(time);
        LocalDB.getInstance().setTimeToEvacuate(time);
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(getString(R.string.our_users_ref));
        usersRef.child(LocalDB.getInstance().getFbid()).child("timeToEvacuate").setValue(time);
    }

    private void saveFBIDToLocalDB(){
        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(getString(R.string.our_users_ref));
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data == null) continue;
                    String uid = data.getValue(User.class).getId();
                    if (uid == null) {
                        Log.d(TAG, "error with saveFBID " + data);
                        continue;
                    }
                    if(uid.equals(FirebaseAuth.getInstance().getUid())){
                        LocalDB.getInstance().setFbid(data.getKey());
                        Log.e(TAG, "fbID key = " + LocalDB.getInstance().getFbid());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        for(PolylineData polylineData: mPolyLinesData){
            Log.d(TAG, "onPolylineClick: toString: " + polylineData.toString());
            if(polyline.getId().equals(polylineData.getPolyline().getId())){
                polylineData.getPolyline().setColor(ContextCompat.getColor(this, R.color.quantum_lightblueA200));
                polylineData.getPolyline().setZIndex(1);
            }
            else{
                polylineData.getPolyline().setColor(ContextCompat.getColor(this, R.color.quantum_grey500));
                polylineData.getPolyline().setZIndex(0);
            }
        }
    }

    private void detectContinouslyGettingToPark(){

    }


    /*public Activity getActivity(Context context)
    {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }*/
}



