package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<Quake>> {

    String mUrl;

    public EarthQuakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(EarthQuakeLoader.class.getSimpleName(), "OnStartLoading() i called");
        forceLoad();
    }

    @Override
    public List<Quake> loadInBackground() {
        Log.i(EarthQuakeLoader.class.getSimpleName(), "OnLoadInBackground() i called");
        if ( mUrl == null){
            return null;
        }
        List<Quake> results = QueryUtils.fetchEarthquakeData(mUrl);
        return results;

    }
}
