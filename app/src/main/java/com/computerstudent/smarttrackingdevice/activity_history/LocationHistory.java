package com.computerstudent.smarttrackingdevice.activity_history;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.computerstudent.smarttrackingdevice.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationHistory extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> latlogArrayList=new ArrayList<LatLng>();
    ArrayList<String>list=new ArrayList<>();
    ArrayList<String>listTime=new ArrayList<>();

    private int id;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Location History");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for( DataSnapshot locationSnapshot : snapshot.getChildren())
                {
                    list.add(locationSnapshot.getValue().toString());
                }
                for(int i=0;i<list.size();i++) {
                    String[] stringLat = list.get(i).split(", ");
                    Arrays.sort(stringLat);
                    String latitude = stringLat[2].split("=")[1];

                    String[] stringLong = list.get(i).split(", ");
                    Arrays.sort(stringLong);
                    String longitude = stringLong[0].split("=")[1];
                    longitude = longitude.replace(longitude.substring(longitude.length() - 1), "");

                    String[] stringTime = list.get(i).split(", ");
                    Arrays.sort(stringLong);
                    String time = stringLong[1].split("=")[1];
                    latlogArrayList.add(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                    listTime.add(time);

                }
                //Toast.makeText(LocationHistory.this, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                try {


                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(0)).title(listTime.get(0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(0), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(1)).title(listTime.get(1)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(1), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(2)).title(listTime.get(2)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(2), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(3)).title(listTime.get(3)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(3), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(4)).title(listTime.get(4)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(4), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(5)).title(listTime.get(5)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(5), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(6)).title(listTime.get(6)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(6), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(7)).title(listTime.get(7)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(7), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(8)).title(listTime.get(8)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(8), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(9)).title(listTime.get(9)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(9), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(10)).title(listTime.get(10)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(10), 10));

                    mMap.addMarker(new MarkerOptions().position(latlogArrayList.get(11)).title(listTime.get(11)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlogArrayList.get(11), 10));

                }
                catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

    }
}