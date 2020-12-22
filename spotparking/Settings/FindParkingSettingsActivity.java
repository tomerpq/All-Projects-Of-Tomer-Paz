package com.example.spotparking.Settings;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.spotparking.databinding.ActivityFindParkingSettingsBinding;


/*Main com.example.spotparking.Settings of the app. com.example.spotparking.Settings of the FindParking activity. Son of FindParking Activity*/
public class FindParkingSettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFindParkingSettingsBinding binding = ActivityFindParkingSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings, new FindParkingSettingsFragment())
//                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MapActivity
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}