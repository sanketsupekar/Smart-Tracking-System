package com.computerstudent.smarttrackingdevice.activity_about;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.computerstudent.smarttrackingdevice.R;

public class contact_us extends AppCompatActivity {
    ImageButton btnFacebook, btnWhatsapp, btnInstagram, btnGithub, btnGmail, btnYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        btnFacebook = findViewById(R.id.imgFacbook);
        btnWhatsapp = findViewById(R.id.imgWhatsapp);
        btnInstagram = findViewById(R.id.imgInstagram);
        btnGithub = findViewById(R.id.imgGithub);
        btnGmail = findViewById(R.id.imgGmail);
        btnYoutube = findViewById(R.id.imgYoutube);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void onClick(View v) {
        if (btnFacebook.equals(v)) {
            Uri uri = Uri.parse("https://www.facebook.com/sanket.supekar.712/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } else if (btnWhatsapp.equals(v)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.link/7fvg7d")));
        } else if (btnInstagram.equals(v)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sanket_supekar_patil/")));
        } else if (btnGithub.equals(v)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SANKETSUPEKAR")));
        } else if (btnGmail.equals(v)) {
            Toast.makeText(this, "sanketsupekar123@gmail.com", Toast.LENGTH_SHORT).show();
        } else if (btnYoutube.equals(v))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCsZS_PHRWkho8l6u8jR2nhg")));
    }
}