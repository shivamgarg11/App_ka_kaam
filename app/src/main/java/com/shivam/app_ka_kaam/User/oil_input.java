package com.shivam.app_ka_kaam.User;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shivam.app_ka_kaam.R;

public class oil_input extends AppCompatActivity {

    static String  timeoil="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_input);

        String pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("USER/OIL/"+pathway);


        TextView datetime=findViewById(R.id.datetime);
        gettimedate(oil_input.this,datetime);



    }



    public static void gettimedate(Context context, final TextView datetime){

        android.location.LocationManager locationManager = (android.location.LocationManager)
                context.getSystemService(android.content.Context.LOCATION_SERVICE);

        android.location.LocationListener locationListener = new android.location.LocationListener() {

            public void onLocationChanged(android.location.Location location) {

                timeoil = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(location.getTime());

                if( location.getProvider().equals(android.location.LocationManager.GPS_PROVIDER)){
                    android.util.Log.d("Location", "Time GPS: " + timeoil); // This is what we want!
                    datetime.setText(timeoil); }
                else {
                    android.util.Log.d("Location", "Time Device (" + location.getProvider() + "): " + timeoil);
                    datetime.setText(timeoil);}


            }

            public void onStatusChanged(String provider, int status, android.os.Bundle extras) {
            }
            public void onProviderEnabled(String provider) {
            }
            public void onProviderDisabled(String provider) {
            }
        };

        if (android.support.v4.content.ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            android.util.Log.d("Location", "Incorrect 'uses-permission', requires 'ACCESS_FINE_LOCATION'");
        }

        locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
        locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 100, 0, locationListener);

        datetime.setText(timeoil);


    }
}


