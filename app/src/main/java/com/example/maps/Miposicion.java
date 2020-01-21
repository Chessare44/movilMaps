package com.example.maps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class Miposicion implements LocationListener {
    public static double latitud;
    public static double longitud;
    public static boolean statusGPS;
    public static Location coordenadas;

    @Override
    public void onLocationChanged(Location loc) {
        latitud=loc.getLatitude();
        longitud=loc.getLongitude();
        coordenadas=loc;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
