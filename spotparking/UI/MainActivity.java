package com.example.spotparking.UI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.spotparking.R;
import com.google.firebase.auth.FirebaseAuth;


/* implemented as a circle in the phone main screen that can
be exited with dragging to X in the center of the phone.
father of buttons-pre-map activity, and login activity.
 */

public class MainActivity extends AppCompatActivity {

    private int whichScreen = 0;//0 for login, 1 for carRegistration, 2 for verifyPermission, 3 for FindParkingActivity.
    private FirebaseAuth mAuth;
    public static final int SYSTEM_ALERT_WINDOW_PERMISSION = 7;
    String channelId = "NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //  Activity was brought to front and not created,
            //  Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            RuntimePermissionForUser();
        }
        startNotification();
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
//        //setContentView(R.layout.activity_main);
//        mAuth = FirebaseAuth.getInstance();

    }

    public void RuntimePermissionForUser() {
        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    private void startNotification() {
        Intent closeButton = new Intent("Download_Cancelled");
        closeButton.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        closeButton.setClass(this, MainActivity.DownloadCancelReceiver.class);

        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0, closeButton, 0);

        RemoteViews notificationView = new RemoteViews(getPackageName(), R.layout.notification_shutdown);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo2).setTicker("Ticker Text").setContent(notificationView);
        notificationView.setOnClickPendingIntent(R.id.Close_Icon, pendingSwitchIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //String channelId = "NOTIFICATION";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        builder.setAutoCancel(true);
        notificationManager.notify(1, builder.build());


    }

    public static class DownloadCancelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.gc();
            System.exit(0);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }}