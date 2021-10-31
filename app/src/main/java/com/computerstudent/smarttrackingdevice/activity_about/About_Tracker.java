package com.computerstudent.smarttrackingdevice.activity_about;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.computerstudent.smarttrackingdevice.Login;
import com.computerstudent.smarttrackingdevice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About_Tracker extends AppCompatActivity {
    TextView txtTrackerId,txtDirection,txtSatellite,txtSpeed,txtQuality,txtBattery;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

                        txtTrackerId=findViewById(R.id.txtTrackerId);
                        txtDirection=findViewById(R.id.txtDirection);
                        txtSatellite=findViewById(R.id.txtSatellites);
                        txtSpeed=findViewById(R.id.txtSpeed);
                        txtQuality=findViewById(R.id.txtQuality);
                        txtBattery=findViewById(R.id.txtBattery);

        databaseReference = FirebaseDatabase.getInstance().getReference("gpsTracker/locationInfo");
        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String direction =snapshot.child("Direction").getValue().toString();
                    String satellite =snapshot.child("Satellites").getValue().toString();
                    String speed =snapshot.child("Speed").getValue().toString();
                    String quality =snapshot.child("SignalQuality").getValue().toString();
                    String battery =snapshot.child("Battery").getValue().toString();

                    txtTrackerId.setText("GPS01");
                    txtDirection.setText(direction);
                    txtSatellite.setText(satellite);
                    txtSpeed.setText(speed + " Kmph");
                    txtQuality.setText(quality);
                    txtBattery.setText(battery + " %");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Network Error ! Check Network Connection", Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}