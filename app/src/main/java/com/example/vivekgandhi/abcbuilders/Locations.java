package com.example.vivekgandhi.abcbuilders;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Locations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
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

        // Add a marker in Sydney and move the camera
        LatLng property1 = new LatLng(19.080922, 72.907986);
        mMap.addMarker(new MarkerOptions().position(property1).title("property1 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property1));

        LatLng property2 = new LatLng(19.044548, 72.820564);
        mMap.addMarker(new MarkerOptions().position(property2).title("property2 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property2));

        LatLng property3 = new LatLng(19.048979, 72.820915);
        mMap.addMarker(new MarkerOptions().position(property3).title("property3 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property3));

        LatLng property4 = new LatLng(19.115096, 72.867296);
        mMap.addMarker(new MarkerOptions().position(property4).title("property4 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property4));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(property2,12.0f));
    }
}
