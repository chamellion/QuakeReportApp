package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /** Sample JSON response for a USGS query */

//     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
//     * This class is only meant to hold static variables and methods, which can be accessed
//     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
//     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils() {
    }
    /**
     * Return a list of {@link Quake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Quake> extractEarthquakes(String earthQuakeJson) {
        if(TextUtils.isEmpty(earthQuakeJson)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Quake> earthquakes = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject reader = new JSONObject(earthQuakeJson);
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONArray features = reader.optJSONArray("features");
            //looping through each feature in the array
            for (int i = 0; i < features.length(); i++){
                JSONObject jsonObject = features.getJSONObject(i);
                JSONObject properties = jsonObject.optJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                Quake quake = new Quake(magnitude, location,time,url);
                earthquakes.add(quake);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG,"Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
        public static URL createURL(String stringUrl){
            URL url = null;
            try {
                url = new URL(stringUrl);
            }catch (MalformedURLException e){
                Log.e(LOG_TAG, "Error creating URL", e);
            }

        return url;
        }
        public static String readFromStream(InputStream inputStream) throws IOException {
                StringBuilder outPut = new StringBuilder();
                if (inputStream != null){
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    while (line != null){
                        outPut.append(line);
                        line = bufferedReader.readLine();
                    }
                }

        return outPut.toString();
        }
        public static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse = "";
                if (url == null){
                    return jsonResponse;
                }

            HttpURLConnection urlConnection = null;
                InputStream inputStream = null;

                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    if (urlConnection.getResponseCode() == 200){
                        inputStream = urlConnection.getInputStream();
                         jsonResponse = readFromStream(inputStream);
                    }else {
                        Log.e(LOG_TAG, "Bad http connection" + urlConnection.getResponseCode());
                    }
                }catch (IOException e){
                    Log.e(LOG_TAG, "Error retrieving information online", e);
                }finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
        return jsonResponse;
        }
    public static List<Quake> fetchEarthquakeData(String requestUrl) {

//        try {
//            Thread.sleep(2000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        // Create URL object
        URL url = createURL(requestUrl);


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List <Quake> earthquake = extractEarthquakes(jsonResponse);

        // Return the {@link Event}
        return earthquake;
    }
}