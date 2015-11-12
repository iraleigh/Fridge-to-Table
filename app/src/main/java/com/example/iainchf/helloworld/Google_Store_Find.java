package com.example.iainchf.helloworld;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

public class Google_Store_Find extends AppCompatActivity implements OnMapReadyCallback {
    public int markCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google__store__find);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void onMapReady(GoogleMap map) {
        // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        map.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        try {
            Location myLocation = locationManager.getLastKnownLocation(provider);
            // set map type
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Get latitude of the current location
            double latitude = myLocation.getLatitude();

            // Get longitude of the current location
            double longitude = myLocation.getLongitude();

            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Show the current location in Google Map
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Zoom in the Google Map
            map.animateCamera(CameraUpdateFactory.zoomTo(14));
            String snippetLatLong = latitude + ", " + longitude;
            map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet(snippetLatLong));
            /*
            gPlacesAPI gPlace = new gPlacesAPI(longitude, latitude);
            List<Places> placesList = gPlace.getList();
            for(Places p : placesList){
                double lat = p.getLat();
                double lng = p.getLng();
                String name = p.getName();
                String address = p.getAddress();
                map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(name).snippet(address));
            }
            */
        } catch(Exception e){
            Toast.makeText(this,"Location Services Not Allowed, change permission",Toast.LENGTH_SHORT);
        }
        /*
         * found out about setOnMapClickListener() from stack overflow
         * http://stackoverflow.com/questions/14013002/google-maps-android-api-v2-detect-touch-on-map
         * but added some of my own code for the title and snippet of each marker.
         *
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
                LatLng nMark = new LatLng(arg0.latitude, arg0.longitude);
                markCount++;
                String markTitle = "New marker " + markCount;
                String markSnippet = arg0.latitude + ", " + arg0.longitude;
                map.addMarker(new MarkerOptions().position(nMark).title(markTitle).snippet(markSnippet));
            }
        });
        */
    }
}
