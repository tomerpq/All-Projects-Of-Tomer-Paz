package com.example.spotparking;

import com.google.android.gms.maps.model.LatLng;

public class Constants {
    public static final int ERROR_DIALOG = 9000;

    /***********keys and codes*************/
    public static final int PERMISSION_REQUEST_ENABLE_GPS = 1000;
    public static final int PERMISSION_REQUEST_ENABLE_FINE_LOCATION = 1001;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static final int AUTOCOMPLETE_REQUEST_CODE = 1;


    /***********default values*************/
    public static final float DEFAULT_ZOOM = 15;
    // default coordinates is tel aviv.
    public static final LatLng DEFAULT_COORDINATES = new LatLng(32.0853, 34.7818);
    public static final float FOCUS_ON_SPOT_ZOOM = 17;

    /**** shared prefrences keys for login ****/
    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String KEY_QUICK_LOGIN = "quick_login ";


}

