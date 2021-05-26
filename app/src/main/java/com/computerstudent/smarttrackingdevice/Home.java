 package com.computerstudent.smarttrackingdevice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.computerstudent.smarttrackingdevice.activity_about.About_Tracker;
import com.computerstudent.smarttrackingdevice.activity_about.about_project;
import com.computerstudent.smarttrackingdevice.activity_about.contact_us;
import com.computerstudent.smarttrackingdevice.activity_battery.Battery;

import com.computerstudent.smarttrackingdevice.activity_call.CallLog;
import com.computerstudent.smarttrackingdevice.activity_location.DeviceLocation;
import com.computerstudent.smarttrackingdevice.activity_history.LocationHistory;
import com.computerstudent.smarttrackingdevice.activity_sms.smsLog;
import com.google.android.material.navigation.NavigationView;

 public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button buttonLocation, buttonHistory, buttonCall, buttonSms, buttonBattery, buttonAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawerLayout);
        coordinatorLayout = findViewById(R.id.coordinateLayout);
        frameLayout = findViewById(R.id.framelayout);
        navigationView = findViewById(R.id.navigationView);
        buttonLocation = findViewById(R.id.buttonLocation);
        buttonHistory = findViewById(R.id.buttonHistory);
        buttonCall = findViewById(R.id.buttonCall);
        buttonSms = findViewById(R.id.buttonSms);
        buttonBattery = findViewById(R.id.buttonBattery);
        buttonAlert = findViewById(R.id.buttonAlert);

       //setUpToolbar();
        actionBarDrawerToggle = new ActionBarDrawerToggle(Home.this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.aboutus:
                        startActivity(new Intent(Home.this, about_project.class));
                        break;
                    case R.id.contactus:
                        startActivity(new Intent(Home.this, contact_us.class));
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
                Intent intent = new Intent(Home.this, smsLog.class);
                startActivity(intent);

            }
        });
        buttonBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Battery.class);
                startActivity(intent);


            }
        });
        buttonAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, About_Tracker.class);
                startActivity(intent);


            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }

}

