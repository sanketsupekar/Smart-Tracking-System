package com.computerstudent.smarttrackingdevice.activity_call;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.computerstudent.smarttrackingdevice.MainActivity;
import com.computerstudent.smarttrackingdevice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CallLog extends AppCompatActivity {
public int textViewId=0;
public LinearLayout lLayout;
public LinearLayout.LayoutParams params;
public TextView showCallLog;
private DatabaseReference databaseReference;
String battery, direction, lat,log,satellite, signalquality, speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);
        showCallLog = (TextView) findViewById(R.id.showCallLog);
        lLayout = (LinearLayout) findViewById(R.id.linearLayout); // Root ViewGroup in which you want to add textviews
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(ContextCompat.checkSelfPermission(CallLog.this,
                Manifest.permission.READ_CALL_LOG)  != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(CallLog.this,
                    Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(CallLog.this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(CallLog.this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {

            databaseReference = FirebaseDatabase.getInstance().getReference("gpsTracker/locationInfo");
            ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        battery =snapshot.child("Battery").getValue().toString();
                        Toast.makeText(CallLog.this,battery,Toast.LENGTH_SHORT).show();
                        getCallDetails();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(CallLog.this,"Fail",Toast.LENGTH_SHORT).show();

                }
            });
        }
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewLayout);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResult) {
        switch (requestCode) {
            case 1: {
                 if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                  if(ContextCompat.checkSelfPermission(CallLog.this,
                          Manifest.permission.READ_CALL_LOG)  == PackageManager.PERMISSION_GRANTED) {

                      Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
                      getCallDetails();

                  }
                } else {
                     Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                 }
                 return;
            }
        }
    }

    private String getCallDetails()
    {
                StringBuffer sb = new StringBuffer();
                Cursor managedCursor = getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, null,
                        android.provider.CallLog.Calls.CACHED_NAME+"= 'GPS Tracker'",null,null );
                int name=managedCursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
                int number = managedCursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
                int type = managedCursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
                int date = managedCursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
                int duration = managedCursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
                while (managedCursor.moveToNext()) {
                    String callname=managedCursor.getString(name);
                    String phNumber = managedCursor.getString(number);
                    String callType = managedCursor.getString(type);
                    String callDate = managedCursor.getString(date);
                    Date callDayType = new Date(Long.valueOf(callDate));
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
                    String dateString = formatter.format(callDayType);
                    String callDuration = managedCursor.getString(duration);
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case android.provider.CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;
                        case android.provider.CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;
                        case android.provider.CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }

                    textViewId++;
                        TextView tv = new TextView(this);
                        tv.setId(textViewId);
                        tv.setWidth(500);
                        tv.setHeight(300);
                        params.setMargins(60, 50, 10, 10);
                        tv.setPadding(20,10,10,10);
                        tv.setLayoutParams(params);
                        tv.setBackground(getResources().getDrawable(R.drawable.receive_round_box));
                        tv.setTextColor(Color.BLACK);
                        tv.setTextSize(15);
                        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);// Prepare textview object programmatically
                        tv.setText(String.valueOf("\nTime : " + dateString+"\n\n        Name: " + callname +
                                "\n        Phone Number: " + phNumber + "\n        CallType: " + dir +
                                "\n        Call Duration in sec: " + callDuration));
                               
                        lLayout.addView(tv);



                }
                managedCursor.close();
                return sb.toString();
    }

}