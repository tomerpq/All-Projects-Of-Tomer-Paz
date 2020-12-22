package com.example.spotparking.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.spotparking.R;
import com.example.spotparking.UI.LoginActivity;
import com.example.spotparking.Utils.SharedPreferencesUtils;
import com.google.firebase.auth.FirebaseAuth;

/*  */
public class MapSettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register as shared preferences listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.map_preferences, rootKey);

        // set a click listener to the share button
        Preference preference = findPreference(getString(R.string.share_button_key));
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent shareIntent = new Intent();
                // for now just sending hi, need to share the app link.
                shareIntent.setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
                        .setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, null));
                return true;
            }
        });
        Preference log_off_preference = findPreference(getString(R.string.log_off_key));
        log_off_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("abc", "onPreferenceClick: ");
                startLogOffAlertDialog();
                getActivity().finish();
                return true;
            }
        });

    }

    private void startLogOffAlertDialog() {
        final Activity thisActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
        builder.setMessage("Are you sure you want to log off?")
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPreferencesUtils.delete(thisActivity);
                        startActivity(new Intent(thisActivity, LoginActivity.class));
                    }
                }).setNegativeButton(android.R.string.no, null);
       builder.create().show();
    }


    // no need for now
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregister
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
