package com.driver.driverapp;



import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.location.Criteria;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

public class MainActivity extends  Activity implements LocationListener {

    final String LOG = "MainActivity";
    private LocationManager locationManager;

   // private static String url = "http://192.168.43.8:4444";

    private TextView latituteField;
    private TextView longitudeField;
    private String provider;


    public void onLocationChanged(final Location location) {

//

        // Called when a new location is found by the network location provider.
        Log.i("Message: ","Location changed, " + location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude());
        double lat = location.getLatitude();
        double log =location.getLongitude();
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(log));

        HashMap postdata = new HashMap();

        String lati = latituteField.getText().toString();
        String longi = longitudeField.getText().toString();

        postdata.put("lati", lati);
        postdata.put("long", longi);

        PostResponseAsyncTask task1 = new PostResponseAsyncTask(MainActivity.this, postdata, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(LOG, s);
                if (s.contains("updated")) {
                    Toast.makeText(MainActivity.this, "Location updated", Toast.LENGTH_LONG).show();

                }
            }
        });
        task1.execute("http://10.0.2.2/shuttle/shuttle.php");
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {}
    public void onProviderEnabled(String provider) {}
    public void onProviderDisabled(String provider) {}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latituteField = (TextView) findViewById(R.id.et1);
        longitudeField = (TextView) findViewById(R.id.et2);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,30000,0,this);

    }
}