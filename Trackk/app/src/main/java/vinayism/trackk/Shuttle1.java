package vinayism.trackk;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Shuttle1 extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    String url = "http://10.0.2.2/shuttle/getlocation.php";
    TextView tv;
    double dlati=0.000 , dlongi=0.000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle1);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.getUiSettings().setZoomGesturesEnabled(true);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);


          // mMap.setOnMyLocationChangeListener(myLocationChangeListener());
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                Showbusposition();
            }
        }
    }

    private void Showbusposition() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TaskExampleRepeating(), 10000, 5000);
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(dlati, dlongi)).title("Main Gate"));
        Toast.makeText(getApplicationContext() , "Location Updated" , Toast.LENGTH_SHORT).show();

    }

    private class TaskExampleRepeating extends TimerTask {

        public void run(){
            System.out.println(new Date() + " ->" + " I will repeat every 5 seconds.");
            final JsonObjectRequest requestmyinfo = new JsonObjectRequest(Request.Method.GET , url ,null , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    // tv.setText(jsonObject.getString("name"));

                    try {
                        String lati = jsonObject.getString("lati");
                        String longi = jsonObject.getString("longi");
                        dlati = new Double(lati);
                        dlongi = new Double(longi);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setUpMap();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Toast.makeText(getApplicationContext() , "Some Error" , Toast.LENGTH_SHORT).show();
                }
            });

            MySingleton.getInstance(Shuttle1.this).addToRequestQueue(requestmyinfo);

        }
    }
}
