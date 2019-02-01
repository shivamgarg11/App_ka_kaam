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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.Java_objects.electricitylastvalue;
import com.shivam.app_ka_kaam.Java_objects.electricityconstants;
import com.shivam.app_ka_kaam.Java_objects.electricity_object;
import com.shivam.app_ka_kaam.MainActivity;
import com.shivam.app_ka_kaam.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class electricity_input extends AppCompatActivity {

    String timeelectricity="";
    String pathway;
    electricitylastvalue[] lastvalue = new electricitylastvalue[1];
    electricityconstants[] constant = new electricityconstants[1];
    AlertDialog alertDialog;
    ProgressBar loader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_input);

        loader=findViewById(R.id.loader);

         pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("USER/ELECTRICITY/"+pathway);


        TextView datetime=findViewById(R.id.datetime);
        gettimedate(electricity_input.this,datetime);



        Button close =findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(electricity_input.this,user.class));
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



    public  void onclickbutton(){

        getprevoiusdata();
        loader.setVisibility(View.VISIBLE);
        FancyToast.makeText(this, "WAIT WHILE WE ARE MAKING EVERYTHING READY ", FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();


        EditText electricityinput1=findViewById(R.id.electricityinput1);
        final int data1=Integer.valueOf(electricityinput1.getText().toString());

        EditText electricityinput2=findViewById(R.id.electricityinput2);
        final int data2=Integer.valueOf(electricityinput2.getText().toString());

        EditText electricityinput3=findViewById(R.id.electricityinput3);
        final int data3=Integer.valueOf(electricityinput3.getText().toString());

        EditText electricityinput4=findViewById(R.id.electricityinput4);
        final int data4=Integer.valueOf(electricityinput4.getText().toString());

        alertDialog = new AlertDialog.Builder(electricity_input.this)
                .setTitle("CONFIRMATION:")
                .setMessage("\nDATA 1: "+data1+"\n\n"+"DATA 2: "+data2+"\n\n"+"DATA 3: "+data3+"\n\n"+"DATA 4: "+data4)
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //WRITING TO FIREBASE

                        final int year=Integer.valueOf(timeelectricity.substring(6,10));
                        final int month=Integer.valueOf(timeelectricity.substring(3,5));
                        final int date=Integer.valueOf(timeelectricity.substring(0,2));

                        //writing value
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference("ELECTRICITY"+pathway).child(year+"").child(month+"");


                        //Writing lastvalue
                        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef1 = database1.getReference("ELECTRICITY"+pathway).child("LASTVALUE");

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.hasChild(date+"")){
                                    electricity_object obj=insertvalues(data1,data2,data3,data4);
                                    myRef.child(date+"").setValue(obj);
                                    FancyToast.makeText(electricity_input.this, "THANK YOU FOR UPDATING ", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    startActivity(new Intent(electricity_input.this, MainActivity.class));
                                    finish();

                                }else{

                                    AlertDialog.Builder override=new AlertDialog.Builder(electricity_input.this)
                                            .setIcon(R.drawable.logoo)
                                            .setTitle("DO YOU WANT TO OVERWRITE THE DATA").setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    electricity_object obj=insertvalues(data1,data2,data3,data4);
                                                    myRef.child(date+"").setValue(obj);
                                                    FancyToast.makeText(electricity_input.this, "THANK YOU FOR UPDATING ", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                                    startActivity(new Intent(electricity_input.this, MainActivity.class));
                                                    finish();

                                                }
                                            });
                                    override.create();
                                    override.show();

                                }

                                electricitylastvalue obj=new electricitylastvalue(date,Integer.valueOf(timeelectricity.substring(11,13)),month,year,data1,data2);
                                myRef1.setValue(obj);
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("TAG", "Failed to read value.", error.toException());
                            }
                        });
                    }
                })
                .create();

    }


    public  electricity_object insertvalues(int input1,int input2,int input3,int input4){
        electricity_object obj = new electricity_object();

        obj.setKwh(input1);
        obj.setKvah(input2);
        obj.setMpf(input3);
        obj.setPpf(input4);
        obj.setCal_pf((input1-lastvalue[0].getKwh())/(input2-lastvalue[0].getKvah()));


        final int year=Integer.valueOf(timeelectricity.substring(6,10));
        final int month=Integer.valueOf(timeelectricity.substring(3,5));
        final int date=Integer.valueOf(timeelectricity.substring(0,2));

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String inputString1 = lastvalue[0].getDate()+" "+lastvalue[0].getMonth()+" "+lastvalue[0].getYear();
        String inputString2 = date+" "+month+" "+year;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = (TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.HOURS)/24)*24;

            if(diff==0){
                diff=Integer.valueOf(timeelectricity.substring(11,13))-lastvalue[0].getTime();
            }
            if(diff==0){
                diff=1;
            }

            obj.setAmount1((obj.getCal_pf()*constant[0].getC1()*constant[0].getC3()*15*24)/diff);
            obj.setAmount2((obj.getCal_pf()*constant[0].getC2()*constant[0].getC3()*15*24)/diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  obj;

    }


    public void getprevoiusdata(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("ELECTRICITY"+pathway).child("LASTVALUE");

        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database1.getReference("ELECTRICITY"+pathway).child("CONSTANTS");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                constant[0] =dataSnapshot.getValue(electricityconstants.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lastvalue[0] =dataSnapshot.getValue(electricitylastvalue.class);
                alertDialog.show();
                loader.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }



    public  void gettimedate(Context context, final TextView datetime){

        android.location.LocationManager locationManager = (android.location.LocationManager)
                context.getSystemService(android.content.Context.LOCATION_SERVICE);

        android.location.LocationListener locationListener = new android.location.LocationListener() {

            public void onLocationChanged(android.location.Location location) {

                timeelectricity = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(location.getTime());

                if( location.getProvider().equals(android.location.LocationManager.GPS_PROVIDER)){
                    android.util.Log.d("Location", "Time GPS: " + timeelectricity); // This is what we want!
                    datetime.setText(timeelectricity);
                    loader.setVisibility(View.GONE);}
                else {
                    android.util.Log.d("Location", "Time Device (" + location.getProvider() + "): " + timeelectricity);
                    datetime.setText(timeelectricity);
                    loader.setVisibility(View.GONE);}


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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(electricity_input.this, user.class));
        finish();
    }

}






