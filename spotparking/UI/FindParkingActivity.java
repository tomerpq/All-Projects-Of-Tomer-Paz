package com.example.spotparking.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotparking.DB.WriteAndReadData;
import com.example.spotparking.databinding.ActivityFindParkingBinding;


import com.example.spotparking.Settings.FindParkingSettingsActivity;


/*Contains Big Button for find parking, and buttong for manual map entering */

public class FindParkingActivity extends AppCompatActivity {

    private HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFindParkingBinding binding = ActivityFindParkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        WriteAndReadData wr = new WriteAndReadData();
//        wr.readCurrentCarDetails();
        initHomeWatcher();
        // floating button to go to the map
        binding.mapFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send intent to map activity without the request to activate find nearest parking function:
                finish();
                startActivity(new Intent(view.getContext(), MapActivity.class));
            }
        });
        // for now just go to the map
        binding.findParkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send intent to map activity with the request to activate find nearest parking function:
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("start_searching", 0);
                finish();
                startActivity(intent);
            }
        });
        //go to the FindParking settings activity
        binding.appSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FindParkingSettingsActivity.class);
                finish();
                startActivity(intent);
            }
        });

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
                startService(new Intent(FindParkingActivity.this, FloatingWidgetShowService.class));
                //finish();
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {//finish();
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mHomeWatcher.stopWatch();
    }
}