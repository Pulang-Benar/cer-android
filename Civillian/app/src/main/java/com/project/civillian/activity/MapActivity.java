package com.project.civillian.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.civillian.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    final Map<String, String> mapLatLong = new HashMap<>();
    private Marker markerMercubuana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        checkLocationPermission();
        if(getIntent() != null){
            Bundle bundle=getIntent().getExtras();
            if(bundle != null){
                String latitude = bundle.getString("latitude");
                String longitude = bundle.getString("longitude");
                System.out.println(" Go to Map Activity - latitude="+latitude+", longitude="+longitude);
                mapLatLong.put("latitude", latitude);
                mapLatLong.put("longitude", longitude);
                Address address = getAddress(latitude, longitude);
                if(address != null){
                    mapLatLong.put("title", address.getThoroughfare());
                    mapLatLong.put("address", address.getAddressLine(0));
                }
            }
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapNotification);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double latitude = getDouble(mapLatLong.get("latitude"));
        Double longitude = getDouble(mapLatLong.get("longitude"));
        LatLng latitudeMercubuana = new LatLng(latitude, longitude);
        markerMercubuana = mMap.addMarker(new MarkerOptions()
                .position(latitudeMercubuana)
                .title(mapLatLong.get("title"))
                .snippet(mapLatLong.get("address")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latitudeMercubuana));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //Set detect user location
//        checkLocationPermission();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    public boolean checkLocationPermission() {
        boolean result = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private Address getAddress(String latitude, String longitude){
        String alamatLengkap = "";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(getDouble(latitude), getDouble(longitude), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!addresses.isEmpty()){
            alamatLengkap = addresses.get(0).getAddressLine(0); //setLatestFormattedAddress
            System.out.println("getThoroughfare -> "+addresses.get(0).getThoroughfare());
            return addresses.get(0);
        } else {
            return null;
        }
    }

    private Double getDouble(String d){
        if(d != null) return Double.valueOf(d);
        else return 0d;
    }

}
