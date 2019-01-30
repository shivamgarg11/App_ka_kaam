package com.shivam.app_ka_kaam.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivam.app_ka_kaam.Java_objects.gas_object;
import com.shivam.app_ka_kaam.Java_objects.lastvalue;
import com.shivam.app_ka_kaam.R;

public class gas_input extends AppCompatActivity {

    static String  timegas="";
    String pathway="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_input);

        pathway=getIntent().getStringExtra("path");
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
                onclickbutton();

            }
        });




    }


    public void onclickbutton(){
        EditText gasinput=findViewById(R.id.gasinput);
        final String data=gasinput.getText().toString();

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

                        int year=Integer.valueOf(timegas.substring(6,10));
                        int month=Integer.valueOf(timegas.substring(3,5));
                        final int date=Integer.valueOf(timegas.substring(0,2));
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference("GAS"+pathway).child(year+"").child(month+"");


                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(date+"")){
                                    gas_object obj=insertvalues(Integer.valueOf(data));
                                    myRef.child(date+"").setValue(obj);
                                }else{




                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("TAG", "Failed to read value.", error.toException());
                            }
                        });

                        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef1 = database1.getReference("GAS"+pathway);
                        lastvalue temp1=new lastvalue(date,Integer.valueOf(timegas.substring(11,13)),month,year,Integer.valueOf(data));
                        myRef1.child("LASTVALUE").setValue(temp1);
                    }
                })
                .create();
        alertDialog.show();
    }







    ///NEED TO DO FROM FIREBASE FOR CONSTANT VALUES
    public  gas_object insertvalues(final int input){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("GAS"+pathway).child("LASTVALUE");


        final lastvalue[] temp =new lastvalue[1];
         final int[] difference = new int[1];
        Log.e("TAG", "onDataChange:1  "+difference[0] );
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        temp[0] = dataSnapshot.getValue(lastvalue.class);
                        difference[0] =temp[0].getValue();
                    Log.e("TAG", "onDataChange: "+difference[0] );

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });





        gas_object obj=new gas_object();
        obj.setInput(input);
        obj.setTime(Integer.valueOf(timegas.substring(11,13)));
        obj.setDifference(input-difference[0]);
        obj.setScm(0);
        obj.setMmbto(0);
        obj.setRide(0);
        obj.setBill(0);

        return  obj;
    }


    public static void gettimedate(Context context, final TextView datetime){

        android.location.LocationManager locationManager = (android.location.LocationManager)
                context.getSystemService(android.content.Context.LOCATION_SERVICE);

        android.location.LocationListener locationListener = new android.location.LocationListener() {

            public void onLocationChanged(android.location.Location location) {

                timegas = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(location.getTime());

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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(gas_input.this, user.class));
        finish();
    }
}
