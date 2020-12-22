package com.example.spotparking.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.spotparking.DB.LocalDB;
import com.example.spotparking.DB.WriteAndReadData;
import com.example.spotparking.R;
import com.example.spotparking.UI.LoginActivity;
import com.example.spotparking.UI.PrivacyPolicyActivity;
import com.example.spotparking.Utils.SharedPreferencesUtils;
import com.google.firebase.auth.FirebaseAuth;

public class FindParkingSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.find_parking_preferences, rootKey);

        // set a click listener to the share button
        Preference share_key_preference = findPreference(getString(R.string.share_button_key));
        share_key_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
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

        Preference policy_key_preference = findPreference(getString(R.string.policy_button_key));
        policy_key_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //startActivity(new Intent().setAction(Intent.ACTION_VIEW).setClassName("com.example.spotparking", "com.example.spotparking.App.UI.PrivacyPolicyActivity"));
                // startActivity(new Intent().setAction(Intent.ACTION_VIEW).setClassName(", "com.example.spotparking.App.UI.PrivacyPolicyActivity"));
                startActivity(new Intent(preference.getContext(), PrivacyPolicyActivity.class));

                return true;
            }
        });
       /* PreferenceScreen screen = (PreferenceScreen)findPreference(getString(R.string.policy_button_key));
        new Intent()
        Intent intent = new Intent(PrivacyPolicyActivity.class);
        screen.setIntent(intent);*/


        //wr.saveDetails(manufactureString,modelString,colorString,numberString);

        Preference car_manufacture_preference = findPreference(getString(R.string.car_manufacture_pref_key));
        car_manufacture_preference.setSummary(LocalDB.getInstance().getManufactureString());
        car_manufacture_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                if (stringValue.equals("")) {
                    Toast.makeText(getActivity(), "Please enter your car's manufacture",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                WriteAndReadData wr = new WriteAndReadData();
                wr.saveCarManufacture(stringValue);
                preference.setSummary(stringValue);
                return true;
            }
        });

        Preference car_model_preference = findPreference(getString(R.string.car_model_pref_key));
        car_model_preference.setSummary(LocalDB.getInstance().getModelString());
        car_model_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                if (stringValue.equals("")) {
                    Toast.makeText(getActivity(), "Please enter your car's model",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                WriteAndReadData wr = new WriteAndReadData();
                wr.saveCarModel(stringValue);
                preference.setSummary(stringValue);
                return true;
            }
        });

        Preference car_color_preference = findPreference(getString(R.string.car_color_pref_key));
        car_color_preference.setSummary(LocalDB.getInstance().getColorString());
        car_color_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                WriteAndReadData wr = new WriteAndReadData();
                wr.saveCarColor(stringValue);
                preference.setSummary(stringValue);
                return true;
            }
        });

        Preference car_number_preference = findPreference(getString(R.string.car_number_pref_key));
        car_number_preference.setSummary(LocalDB.getInstance().getNumberString());
        car_number_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                WriteAndReadData wr = new WriteAndReadData();
                wr.saveCarNumber(stringValue);
                // WriteData(stringValue);
                preference.setSummary(stringValue);
                return true;
            }
        });

        Preference feedback_key_preference = findPreference(getString(R.string.feedback_button_key));
        feedback_key_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);

                // feedbackIntent.setData(Uri.parse("mailto:contactus.spotparking@gmail.com"));
                //startActivity(feedbackIntent);

                //feedbackIntent.setType("message/rfc822");
                feedbackIntent.setData(Uri.parse("mailto:"));
                //feedbackIntent.setDataAndType(Uri.parse("mailto:"), "message/rfc822");
                String[] recipients = new String[]{"", "contactus.spotparking@gmail.com"};
                feedbackIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                //feedbackIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "This is my text" );
                //feedbackIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(feedbackIntent, "Send Email"));

                //feedbackIntent.setAction(Intent.ACTION_SENDTO).setData("contactus.spotparking@gmail.com").putExtra(Intent.EXTRA_TEXT, R.string.share_text)
                // .setType("text/plain");
                //startActivity(Intent.createChooser(feedbackIntent, null));
                return true;
            }
        });

        Preference log_off_preference = findPreference(getString(R.string.log_off_key));
        log_off_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startLogOffAlertDialog();
                return true;
            }
        });

    }

    private void startLogOffAlertDialog() {
        final Activity thisActivity = getActivity();
        if (thisActivity == null) {
            Log.d("error", "startLogOffAlertDialog: ");
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
        builder.setMessage("Are you sure you want to log off?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPreferencesUtils.delete(getContext());
                        startActivity(new Intent(thisActivity, LoginActivity.class));
                        thisActivity.finish();
                    }
                }).setNegativeButton(android.R.string.no, null)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alert = builder.create();
        alert.show();
    }

}