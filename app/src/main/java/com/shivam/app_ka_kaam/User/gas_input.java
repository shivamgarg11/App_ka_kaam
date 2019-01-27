package com.shivam.app_ka_kaam.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shivam.app_ka_kaam.R;

public class gas_input extends AppCompatActivity {

    static String  timegas="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_input);

        final String pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("USER/GAS/"+pathway);


        TextView datetime=findViewById(R.id.datetime);
       gettimedate(gas_input.this,datetime);




        Button close =findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gas_input.this,user.class));
                finish();
            }
        });


        Button done =findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText gasinput=findViewById(R.id.gasinput);
                String data=gasinput.getText().toString();

                final AlertDialog alertDialog = new AlertDialog.Builder(gas_input.this)
                        .setTitle("CONFIRMATION:")
                        .setMessage("\nDATA : "+data+"\n\n")
                        .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //WRITING TO FIREBASE
                               FirebaseDatabase database = FirebaseDatabase.getInstance();
                               DatabaseReference myRef = database.getReference("GAS");
                               myRef.child(pathway).child("year").child("month").child("date").setValue("Hello, World!");


                            }
                        })
                        .create();
                        alertDialog.show();

            }
        });




    }


    public static void gettimedate(Context context, final TextView datetime){

        android.location.LocationManager locationManager = (android.location.LocationManager)
                context.getSystemService(android.content.Context.LOCATION_SERVICE);

        android.location.LocationListener locationListener = new android.location.LocationListener() {

            public void onLocationChanged(android.location.Location location) {

                timegas = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").format(location.getTime());

                if( location.getProvider().equals(android.location.LocationManager.GPS_PROVIDER)){
                    android.util.Log.d("Location", "Time GPS: " + timegas); // This is what we want!
                    datetime.setText(timegas); }
                else {
                    android.util.Log.d("Location", "Time Device (" + location.getProvider() + "): " + timegas);
                    datetime.setText(timegas);}


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

        datetime.setText(timegas);


    }
}
