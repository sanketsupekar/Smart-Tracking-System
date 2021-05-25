package com.computerstudent.smarttrackingdevice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText trackerid, password;
    Button login;
    boolean loginComplete;
    String validTrackerid;
    String validPassword;
    private DatabaseReference databaseReference;
    SharedPreferences sharedPreferance;

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("gpsTracker/login");
        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    validTrackerid=snapshot.child("username").getValue().toString();
                    validPassword=snapshot.child("password").getValue().toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Network Error ! Check Network Connection", Toast.LENGTH_SHORT).show();

            }
        });
        sharedPreferance=getSharedPreferences(getString(R.string.peferance_file_name),Context.MODE_PRIVATE);
        Boolean isLoggedIn=sharedPreferance.getBoolean("isLoggedIn",false);
        setContentView(R.layout.activity_login);
        if(isLoggedIn)
        {
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }

        this.setTitle("Login");
        trackerid = findViewById(R.id.textTrackerId);
        password = findViewById(R.id.textPassword);
        login = findViewById(R.id.buttonLogin);


        String inputTrakerid = trackerid.getText().toString();
         String inputPassword = password.getText().toString();

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                trackeridInvalid();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputTrakerid = trackerid.getText().toString();
                String inputPassword = password.getText().toString();
                trackeridInvalid();
                passwordInvalid();
                if ((inputTrakerid.equals(validTrackerid)) && (inputPassword.equals(validPassword))) {
                    saveSharedPreferances();
                    startActivity(new Intent(Login.this, Home.class));
                    loginComplete=true;
                } else {
                    Toast.makeText(Login.this, "Invalid Tracker ID OR Password", Toast.LENGTH_SHORT).show();
                    trackerid.setText("");
                    password.setText("");
                }
            }
        });
    }
void trackeridInvalid()
{
    String inputTrakerid = trackerid.getText().toString();
    if(TextUtils.isEmpty(inputTrakerid)) {
        trackerid.setError("Enter Tracker Id");
        return;
    }
}
    void passwordInvalid()
    {
        String inputPassword = password.getText().toString();
        if(TextUtils.isEmpty(inputPassword)) {
            password.setError("Enter Password");
            return;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
 private void saveSharedPreferances()
 {
    sharedPreferance.edit().putBoolean("isLoggedIn",true).apply();
 }
}
