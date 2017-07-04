package com.example.matthew.myapplication;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mLocation;

    private static final String TAG = MapsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //get location
        gpsTracker = new GPSTracker(getApplicationContext());
        mLocation = gpsTracker.getLocation();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //do custom map style shit
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        //add a marker at current locatin
        LatLng whereuare = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(whereuare).title("You are here").icon(BitmapDescriptorFactory.fromResource(R.drawable.youarehere)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(whereuare));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(whereuare,18.0f) );

        //add markers around player
        mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude()+.001,mLocation.getLongitude())).title("Clickable event").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).alpha(0.7f));
        mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude()+.003,mLocation.getLongitude())).title("Nonclickable event").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).alpha(0.7f));

        //make a radius around you :) this one is the sight radius
        CircleOptions circleSeeable = new CircleOptions()
                .center(whereuare)
                .fillColor(Color.argb(50,77,64,0))
                .strokeColor(Color.argb(130,0,77,64))
                .strokeWidth(2)
                .radius(250); // In meters
        //make a radius around you :) this one is the clickable radius
        CircleOptions circleClickable = new CircleOptions()
                .center(whereuare)
                .fillColor(Color.argb(130,0,77,64))
                .strokeWidth(2)
                .strokeColor(Color.argb(130,0,77,64))
                .radius(100); // In meters

// Get back the mutable Circle
        Circle circlesee = mMap.addCircle(circleSeeable);
        Circle circleclick = mMap.addCircle(circleClickable);
       }
}

/**
 https://maps.googleapis.com/maps/api/staticmap?key=YOUR_API_KEY&center=-34.03145298593056,151.03033592700953&zoom=16&format=png&maptype=roadmap&style=element:geometry%7Ccolor:0xf5f5f5&style=element:labels.icon%7Cvisibility:off&style=element:labels.text.fill%7Ccolor:0x616161&style=element:labels.text.stroke%7Ccolor:0xf5f5f5&style=feature:administrative.land_parcel%7Celement:labels%7Cvisibility:off&style=feature:administrative.land_parcel%7Celement:labels.text.fill%7Ccolor:0xbdbdbd&style=feature:landscape.man_made%7Celement:labels.icon%7Cvisibility:off&style=feature:landscape.man_made%7Celement:labels.text%7Cvisibility:off&style=feature:landscape.man_made%7Celement:labels.text.fill%7Cvisibility:off&style=feature:landscape.man_made%7Celement:labels.text.stroke%7Cvisibility:off&style=feature:poi%7Celement:geometry%7Ccolor:0xeeeeee&style=feature:poi%7Celement:labels.text%7Cvisibility:off&style=feature:poi%7Celement:labels.text.fill%7Ccolor:0x757575&style=feature:poi.park%7Celement:geometry%7Ccolor:0xe5e5e5&style=feature:poi.park%7Celement:labels.text.fill%7Ccolor:0x9e9e9e&style=feature:road%7Celement:geometry%7Ccolor:0xffffff&style=feature:road.arterial%7Celement:labels.text.fill%7Ccolor:0x757575&style=feature:road.highway%7Celement:geometry%7Ccolor:0xdadada&style=feature:road.highway%7Celement:labels.text.fill%7Ccolor:0x616161&style=feature:road.local%7Celement:labels%7Cvisibility:off&style=feature:road.local%7Celement:labels.text.fill%7Ccolor:0x9e9e9e&style=feature:transit.line%7Celement:geometry%7Ccolor:0xe5e5e5&style=feature:transit.station%7Celement:geometry%7Ccolor:0xeeeeee&style=feature:water%7Celement:geometry%7Ccolor:0xc9c9c9&style=feature:water%7Celement:geometry.fill%7Ccolor:0x006264&style=feature:water%7Celement:labels.text.fill%7Ccolor:0x9e9e9e&size=480x360

 **/