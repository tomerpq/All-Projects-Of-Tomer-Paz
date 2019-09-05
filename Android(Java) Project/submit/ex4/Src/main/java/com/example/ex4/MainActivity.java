package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    //starting the APP:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
    }
    //Connect and move to the joystick window(by clicking on connect button!):
    public void connectAndStartJoystickWindow(View view){
        //getting the textboxes we typed into:
        EditText editTextIP = (EditText)(findViewById(R.id.editTextIP));
        EditText editTextPort = (EditText)(findViewById(R.id.editTextPort));
        //build intent to be sent to joystick activity
        Intent intent = new Intent(this,DisplayJoystickActivity.class);
        //extracting the ip and port from the textboxes we typed into:
        String ip = editTextIP.getText().toString();
        String port = editTextPort.getText().toString();
        //puting the ip and port in the intent to be sent to joystick activity:
        intent.putExtra("ourIP",ip);
        intent.putExtra("ourPort",port);
        //starting the activity of the joystick with the intent that got the values from above:(the joystick activity will use them to connect(and later disconnect - when its done), to and from the simulator's server.
        startActivity(intent);
    }

}
