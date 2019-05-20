package mx.job.potrobus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import mx.job.potrobus.Interfaces.LocationAPI;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private LocationManager locationManager;

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        DrawerLayout mDrawerlayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout,R.string.abrir,R.string.cerrar);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_json));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //TODO: Request permissions to user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng mLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 18), 3000, null);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                }
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng mLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                if (lastLocation.hasBearing()) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(mLatLng)             // Sets the center of the map to current location
                            .zoom(15)                   // Sets the zoom
                            .bearing(lastLocation.getBearing()) // Sets the orientation of the camera to east
                            .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }else{
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 18), 2000, null);
                }
                return true;
            }
        });
        setRepeatingAsyncTask();
    }

    private void setRepeatingAsyncTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            GetLocationTask getLocationTask = new GetLocationTask();
                            getLocationTask.execute();
                            System.out.println("GetLocationTask is running");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 10*1000);  // interval of one minute: 60*1000
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static class GetLocationTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params){
            //TODO: Get last location from api
            String responseLocation = "";
            String URL = "http://192.168.0.17/potrobus/public/location/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LocationAPI locationJsonAPI = retrofit.create(LocationAPI.class);
            Call<mx.job.potrobus.Entities.Location> call = locationJsonAPI.getLastLocation();
            try {
                mx.job.potrobus.Entities.Location body = call.execute().body();
                if (body!=null){
                    responseLocation = new Gson().toJson(body);
                    System.out.println(responseLocation);
                }else{
                    System.out.println("Body response is null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            call.cancel();
            return responseLocation;
        }

        @Override
        protected void onPostExecute(String lastLocation) {
            super.onPostExecute(lastLocation);
            //TODO: Show the "bus" on map
            if(!lastLocation.isEmpty()){
                mx.job.potrobus.Entities.Location busLocation = new Gson().fromJson(lastLocation, mx.job.potrobus.Entities.Location.class);
                // Creating a marker
                MarkerOptions bus = new MarkerOptions();
                // Setting the position for the marker
                bus.position(new LatLng(busLocation.getLat(), busLocation.getLng()))
                        .title("Bus "+busLocation.getId());
                        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus_background));
                mMap.addMarker(bus);
            }else{
                System.out.println("LastLocation is empty");
            }
        }
    }
}
