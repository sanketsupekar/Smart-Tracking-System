package com.computerstudent.smarttrackingdevice.onBordingSlide;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.computerstudent.smarttrackingdevice.R;

public class SliderForeground extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderForeground(Context context){
        this.context = context;
    }
    //Arrays
    public int[] slide_images = {

            R.drawable.location_icon,
            R.drawable.sms_icon,
            R.drawable.call_icon,
            R.drawable.notification_icon,
            R.drawable.autolocation_icon,
            R.drawable.battery_icon,
    } ;
    public  String[] slide_headings = {

            "LOCATION",
            "MESSAGE",
            "CALL",
            "NOTIFICATION",
            "AUTO SAVE LOCATION",
            "BATTERY"
    };

    public String[] slide_desc = {

            "Find the Correct Location of the Tracking Device Anytime and Anywhere",
            "Get an Emergency Message from the Tracking Device if there is an Internet Problem",
            "Get an Emergency Missed Call from the Tracking Device if there is an Internet Problem",
            "Alert Notification from the Tracking Device",
            "Automatically Save DEVICE LOCATION with Time and Date",
            "Information about the Battery life of the Tracking Device",
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override

    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.activity_slider_foreground, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}