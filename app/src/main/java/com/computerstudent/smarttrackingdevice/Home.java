 package com.computerstudent.smarttrackingdevice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.computerstudent.smarttrackingdevice.activity_about.About;
import com.computerstudent.smarttrackingdevice.activity_battery.Battery;

import com.computerstudent.smarttrackingdevice.activity_call.CallLog;
import com.computerstudent.smarttrackingdevice.activity_location.DeviceLocation;
import com.computerstudent.smarttrackingdevice.activity_history.LocationHistory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    Button buttonLocation, buttonHistory, buttonCall, buttonSms, buttonBattery, buttonAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        coordinatorLayout = findViewById(R.id.coordinateLayout);
        toolbar = findViewById(R.id.toolBar);
        frameLayout = findViewById(R.id.framelayout);
        navigationView = findViewById(R.id.navigationView);
        buttonLocation = findViewById(R.id.buttonLocation);
        buttonHistory = findViewById(R.id.buttonHistory);
        buttonCall = findViewById(R.id.buttonCall);
        buttonSms = findViewById(R.id.buttonSms);
        buttonBattery = findViewById(R.id.buttonBattery);
        buttonAlert = findViewById(R.id.buttonAlert);

        setUpToolbar();
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Home.this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.aboutus:
                        Toast.makeText(getApplicationContext(), "About Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.feedback:
                        Toast.makeText(getApplicationContext(), "Feedback", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, DeviceLocation.class);
                startActivity(intent);


            }
        });
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LocationHistory.class);
                startActivity(intent);
                Toast.makeText(Home.this, "History Enter", Toast.LENGTH_LONG).show();

            }
        });
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, CallLog.class);
                startActivity(intent);

            }
        });
        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "SMS Enter", Toast.LENGTH_LONG).show();

            }
        });
        buttonBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Battery.class);
                startActivity(intent);
                Toast.makeText(Home.this, "Battery Enter", Toast.LENGTH_LONG).show();

            }
        });
        buttonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, About.class);
                startActivity(intent);
                Toast.makeText(Home.this, "Alert Enter", Toast.LENGTH_LONG).show();

            }
        });


    }


    void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }

}

