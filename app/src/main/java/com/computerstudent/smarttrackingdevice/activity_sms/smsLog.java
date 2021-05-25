package com.computerstudent.smarttrackingdevice.activity_sms;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.computerstudent.smarttrackingdevice.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class smsLog extends AppCompatActivity {

    public int textViewId=0;
    public LinearLayout lLayout;
    public LinearLayout.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_log);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lLayout = findViewById(R.id.linearLayout);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        readSms();
        if(ContextCompat.checkSelfPermission(smsLog.this,
                Manifest.permission.READ_SMS)  != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(smsLog.this,
                    Manifest.permission.READ_SMS)) {
                ActivityCompat.requestPermissions(smsLog.this,
                        new String[]{Manifest.permission.READ_SMS}, 1);
            } else {
                ActivityCompat.requestPermissions(smsLog.this,
                        new String[]{Manifest.permission.READ_SMS}, 1);
            }
        }
        else {
            readSms();
        }
        final ScrollView scrollView = findViewById(R.id.scrollViewLayout);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }

    public void readSms(){

        Uri uri = Uri.parse("content://sms/inbox");
        String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
        Cursor c = getContentResolver().query(uri, projection, "address='+917040715511'" ,null,"date desc");
        startManagingCursor(c);
        // Read the sms data
        if(c.moveToFirst()) {
            int index_Address = c.getColumnIndex("address");
            int index_Person = c.getColumnIndex("person");
            int index_Body = c.getColumnIndex("body");
            int index_Date = c.getColumnIndex("date");
            int index_Type = c.getColumnIndex("type");
            do {
                String strAddress = c.getString(index_Address);
                int intPerson = c.getInt(index_Person);
                String strbody = c.getString(index_Body);
                long longDate = c.getLong(index_Date);
                Date callDayType = new Date(longDate);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
                String dateString = formatter.format(callDayType);
                int int_Type = c.getInt(index_Type);

                textViewId++;
                TextView tv = new TextView(this);
                tv.setId(textViewId);
                tv.setWidth(500);
                tv.setHeight(300);
                tv.setBackground(getResources().getDrawable(R.drawable.receive_round_box));
                params.setMargins(60, 50, 10, 10);
                tv.setPadding(20,10,10,10);
                tv.setLayoutParams(params);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(15);
                tv.setTypeface(tv.getTypeface(), Typeface.BOLD);// Prepare textview object programmatically
                tv.setText(String.valueOf("\nTime : " + dateString+"\n\n        Number : " + strAddress +
                        "\n        Message : " + strbody));
                lLayout.addView(tv);
            } while (c.moveToNext());


        }
        c.close();
        // notifying listview adapter
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