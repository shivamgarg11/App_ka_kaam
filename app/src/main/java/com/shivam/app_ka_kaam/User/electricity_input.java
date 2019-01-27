package com.shivam.app_ka_kaam.User;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shivam.app_ka_kaam.R;

public class electricity_input extends AppCompatActivity {

    static String timeelectricity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_input);

        String pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("USER/ELECTRICITY/"+pathway);


        TextView datetime=findViewById(R.id.datetime);
        gettimedate(electricity_input.this,datetime);


    }


    public static void gettimedate(Context context, final TextView datetime){

        android.location.LocationManager locationManager = (android.location.LocationManager)
                context.getSystemService(android.content.Context.LOCATION_SERVICE);

        android.location.LocationListener locationListener = new android.location.LocationListener() {

            public void onLocationChanged(android.location.Location location) {

                timeelectricity = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(location.getTime());

                if( location.getProvider().equals(android.location.LocationManager.GPS_PROVIDER)){
                    android.util.Log.d("Location", "Time GPS: " + timeelectricity); // This is what we want!
                    datetime.setText(timeelectricity); }
                else {
                    android.util.Log.d("Location", "Time Device (" + location.getProvider() + "): " + timeelectricity);
                    datetime.setText(timeelectricity);}


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

        datetime.setText(timeelectricity);


    }
}



