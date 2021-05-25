package com.computerstudent.smarttrackingdevice.activity_battery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.computerstudent.smarttrackingdevice.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Battery extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ProgressBar batteryProgressBar;
    private EditText batteryPercent;
    private String updateBatteryPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        batteryPercent=findViewById(R.id.showBatteryPercent);
        batteryProgressBar=findViewById(R.id.vertical_progressbar);
        databaseReference = FirebaseDatabase.getInstance().getReference("gpsTracker/locationInfo");
        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    updateBatteryPercent=snapshot.child("Battery").getValue().toString();
                    batteryProgressBar.setProgress(Integer.parseInt(updateBatteryPercent));
                    batteryPercent.setText(updateBatteryPercent+"%");
                  //  addNotification("Battery Alert",updateBatteryPercent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addNotification(String msg,String updateBatteryPercent) {
        String message,batteryPercent;
        message=msg;
        batteryPercent=updateBatteryPercent;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background) //set icon for notification
                        .setContentTitle(msg) //set title of notification
                        .setContentText(batteryPercent+"% Battery Remainning")//this is notification message
                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        Intent notificationIntent = new Intent(this, Battery.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        long[] pattern = {500,500};
        builder.setVibrate(pattern);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}