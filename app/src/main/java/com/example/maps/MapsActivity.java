package com.example.maps;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, View.OnClickListener {

    private GoogleMap mMap;
    Button loca;
    Button car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        enableMyLocation();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(this);

        Button loca = (Button) findViewById(R.id.btnlocali);
        Button car = (Button) findViewById(R.id.btncar);

        loca.setOnClickListener(this);
        car.setOnClickListener(this);

        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miPosicion();
            }
        });

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
    }

    private void miPosicion() {

        LocationManager objLocation = null;
        Miposicion objLocListener;
        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener = new Miposicion();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }

        if(objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            if(Miposicion.latitud != 0){

                double lat = Miposicion.coordenadas.getLatitude();
                double lon = Miposicion.coordenadas.getLongitude();
                Toast.makeText(
                        MapsActivity.this,
                        "latitud"+lat+"\n Longitud"+lon,
                        Toast.LENGTH_SHORT).show();
                LatLng miubica = new LatLng(lat, lon);
                Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(miubica)
                        .title("Mi ubicacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miubica));
                CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(16);
                mMap.animateCamera(ZoomCam);
            }else{
                Toast.makeText( MapsActivity.this, "la localizacion no está funcionando", Toast.LENGTH_SHORT).show();
            }
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(MapsActivity.this);
            alert.setTitle("GPS NO ESTÁ ACTIVO");
            alert.setMessage("Conectando con GPS");
            alert.setPositiveButton("OK", null);
            alert.create().show();
        }

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Toast.makeText(
                MapsActivity.this,
                "latitud"+latLng.latitude+"\n Longitud"+latLng.longitude,
                Toast.LENGTH_SHORT).show();
        Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Mi ubicacion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

    }

    @Override
    public void onClick(View v) {
        if (v==loca){
           
        }
    }
}
