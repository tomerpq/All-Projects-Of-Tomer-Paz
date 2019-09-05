package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJoystickActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_display_joystick);
        //get the Intent that caused by connect button, that started this activity. extract the ip and port and connect to the server of the simulator as client to start send stuff to it:
        Intent intent = getIntent();
        //getting ip and port from the main view with the intent sent from there:
        String ip = intent.getStringExtra("ourIP");
        String port = intent.getStringExtra("ourPort");
        Connect con = Connect.getInstance();
        //connecting to the simulator:
        con.connect(ip, port);
        Joystick joystick = new Joystick(this);
        //starting joystick view:
        setContentView(joystick);
    }

    @Override
    public void onDestroy() {
        //disconnecting from the simulator first:
        Connect con = Connect.getInstance();
        con.disconnect();
        //destroying joystick activity:
        super.onDestroy();
    }

}
