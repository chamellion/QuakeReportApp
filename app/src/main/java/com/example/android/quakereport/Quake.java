package com.example.android.quakereport;

public class Quake {

        private  double mMag;
        private String mLocation;
        private long mDate;
        private String mUrl;


    public Quake(double Mag, String Location, long Date, String Url){
            mMag = Mag;
            mLocation = Location;
            mDate = Date;
            mUrl = Url;
        }

        public double getMag() {
            return mMag;
        }

        public String getLocation() {
            return mLocation;
        }

        public long getDate() { return mDate; }

        public String getUrl() { return mUrl; }

    @Override
        public String toString() {
            return "Quake{" +
                    "mMag=" + mMag +
                    ", mLocation='" + mLocation + '\'' +
                    ", mDate='" + mDate + '\'' +
                    '}';
        }
    }


