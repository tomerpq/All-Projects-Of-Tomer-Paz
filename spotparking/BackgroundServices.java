package com.example.spotparking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BackgroundServices extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
         here we will fetch and instore data - fetch from GOOGLE VIRTUAL Machine with our Algorithms
         and store to the Virtaul Machine and maybe to our FB DB.
         */
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {

    }
}
