package com.computerstudent.smarttrackingdevice.onBordingSlide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.computerstudent.smarttrackingdevice.Home;
import com.computerstudent.smarttrackingdevice.Login;
import com.computerstudent.smarttrackingdevice.R;

public class SliderBackground extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderForeground sliderAdapter;
    SharedPreferences sharedPreferance;
    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferance=getSharedPreferences(getString(R.string.peferance_file_name),Context.MODE_PRIVATE);
        Boolean isLoggedIn=sharedPreferance.getBoolean("isLoggedIn",false);
        setContentView(R.layout.activity_slider_background);
        if(isLoggedIn)
        {
            startActivity(new Intent(SliderBackground.this, Login.class));
            finish();
        }


        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);


        sliderAdapter = new SliderForeground(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        //OnClickListeners

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);




    }
});

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSlideViewPager.setCurrentItem(mCurrentPage - 1);

            }
        });

    }

    public void addDotsIndicator(int position){

        mDots = new TextView[6];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length > 0){

            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            mCurrentPage = i;
            if(i==7)
            {
                Intent intent = new Intent(SliderBackground.this, Login.class);
                saveSharedPreferances();

                startActivity(intent);
            }
            else if(i==0){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");

            } else if (i == mDots.length - 1){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");

            }
            else {

              mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        if(mNextBtn.getText()=="Finish")
        {
            mNextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SliderBackground.this, Login.class);
                    startActivity(intent);
                }
            });

        }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

    };
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