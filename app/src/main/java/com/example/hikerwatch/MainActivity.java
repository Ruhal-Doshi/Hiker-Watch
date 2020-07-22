package com.example.hikerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView latitudeTextView ;
    TextView longitudeTextView ;
    TextView altitudeTextView ;
    TextView addressTextView ;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults!=null&&grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView2);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView2);
        altitudeTextView = (TextView) findViewById(R.id.altitudeTextView2);
        addressTextView = (TextView) findViewById(R.id.addressTextView2);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                double alt = location.getAltitude();

                latitudeTextView.setText(Double.toString(lat));
                longitudeTextView.setText(Double.toString(lng));
                altitudeTextView.setText(Double.toString(alt));

                Log.i("Support Location",location.toString());

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list = geocoder.getFromLocation(lat,lng,1);
                    if(list!=null&&list.size()!=0){
                        String address = list.get(0).getAddressLine(0);
                        addressTextView.setText(address);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                double alt = location.getAltitude();

                latitudeTextView.setText(Double.toString(lat));
                longitudeTextView.setText(Double.toString(lng));
                altitudeTextView.setText(Double.toString(alt));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list = geocoder.getFromLocation(lat,lng,1);
                    if(list!=null&&list.size()!=0){
                        String address = list.get(0).getAddressLine(0);
                        addressTextView.setText(address);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}