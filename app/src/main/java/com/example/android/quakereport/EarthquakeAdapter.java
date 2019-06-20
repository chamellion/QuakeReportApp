package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Quake> {
    String locationOffset;
    String primaryLocation;
    private static final String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(Context context, List<Quake> quakes) {
        super(context, 0, quakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.quake_item,parent, false);
        }
        Quake currentQuake = getItem(position);
        TextView magnitudeData = (TextView) listItemView.findViewById(R.id.magnitude);
        String formattedMag = formatDecimal(currentQuake.getMag());
        magnitudeData.setText(formattedMag);

        String originalLocation = currentQuake.getLocation();
        if (originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        TextView primaryLocations = (TextView) listItemView.findViewById(R.id.location_offset);
        primaryLocations.setText(locationOffset);
        TextView locationData= (TextView) listItemView.findViewById(R.id.location);
        locationData.setText(primaryLocation);
        Date date = new Date(currentQuake.getDate());
        TextView dateData  = (TextView) listItemView.findViewById(R.id.date);
         String formattedDate =  formatDate(date);
         dateData.setText(formattedDate);
         TextView timeData = (TextView) listItemView.findViewById(R.id.time);
         String formattedTime = formatTime(date);
         timeData.setText(formattedTime);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeData.getBackground();
        int magnitudeColor = getMagnitudeColor(currentQuake.getMag());
        magnitudeCircle.setColor(magnitudeColor);
        return listItemView;
    }
    private String formatDate(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return simpleDateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject){
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("h:mm a");
        return simpleTimeFormat.format(dateObject);
    }
    private String formatDecimal(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
                default:
                    magnitudeColorResourceId = R.color.magnitude10plus;
                    break;
        }  return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
