package com.shivam.app_ka_kaam.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.Java_objects.gas_object;
import com.shivam.app_ka_kaam.Java_objects.gasconstants;
import com.shivam.app_ka_kaam.Java_objects.gaslastvalue;
import com.shivam.app_ka_kaam.MainActivity;
import com.shivam.app_ka_kaam.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class gas_input extends AppCompatActivity {

     String  timegas="";
    String pathway="";
     gaslastvalue[] lastvalue = new gaslastvalue[1];
     gasconstants[] constant = new gasconstants[1];
    AlertDialog alertDialog;
    ProgressBar loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_input);

        loader=findViewById(R.id.loader);

        pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("USER/GAS/"+pathway);


        TextView datetime=findViewById(R.id.datetime);
        gettimedate(datetime);


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


    public void onclickbutton() {
        EditText gasinput = findViewById(R.id.gasinput);
        final String data = gasinput.getText().toString();

        if (data.length() == 0) {
            FancyToast.makeText(this, "PLEASE ENTER THE INPUT ", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

        } else {

            getprevoiusdata();
            loader.setVisibility(View.VISIBLE);
            FancyToast.makeText(this, "WAIT WHILE WE ARE MAKING EVERYTHING READY ", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();


            alertDialog = new AlertDialog.Builder(gas_input.this)
                    .setIcon(R.drawable.logoo)
                    .setTitle("CONFIRMATION")
                    .setMessage("\nDATA : " + data + "\n\n")
                    .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //WRITING TO FIREBASE

                            final int year = Integer.valueOf(timegas.substring(6, 10));
                            final int month = Integer.valueOf(timegas.substring(3, 5));
                            final int date = Integer.valueOf(timegas.substring(0, 2));

                            //writing value
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference("GAS" + pathway).child(year + "").child(month + "");


                            //Writing lastvalue
                            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef1 = database1.getReference("GAS" + pathway).child("LASTVALUE");

                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.hasChild(date + "")) {
                                        gas_object obj = insertvalues(Double.valueOf(data));
                                        myRef.child(date + "").setValue(obj);
                                        FancyToast.makeText(gas_input.this, "THANK YOU FOR UPDATING \n\n YOU HAVE BEEN LOGGED OUT", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                        startActivity(new Intent(gas_input.this, MainActivity.class));
                                        finish();

                                    } else {

                                        AlertDialog.Builder override = new AlertDialog.Builder(gas_input.this)
                                                .setIcon(R.drawable.logoo)
                                                .setTitle("DO YOU WANT TO OVERWRITE THE DATA").setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                })
                                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        gas_object obj = insertvalues(Double.valueOf(data));
                                                        myRef.child(date + "").setValue(obj);
                                                        FancyToast.makeText(gas_input.this, "THANK YOU FOR UPDATING \n\n YOU HAVE BEEN LOGGED OUT", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                                        startActivity(new Intent(gas_input.this, MainActivity.class));
                                                        finish();



                                                    }
                                                });
                                        override.create();
                                        override.show();

                                    }

                                    gaslastvalue obj = new gaslastvalue(date, Integer.valueOf(timegas.substring(11, 13)), month, year, Double.valueOf(data));
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

    }




    public  gas_object insertvalues(double input){
        gas_object obj = new gas_object();

           Log.e("TAG", "insertvalues: " + lastvalue[0].getValue());
           obj.setInput(input);
           obj.setTime(Integer.valueOf(timegas.substring(11, 13)));
           obj.setDifference(input-lastvalue[0].getValue());
           obj.setScm(obj.getDifference()*constant[0].getC1());
           obj.setMmbto(obj.getScm()*constant[0].getC2()*constant[0].getC3());
           obj.setRide(obj.getMmbto()*constant[0].getC4());

        final int year=Integer.valueOf(timegas.substring(6,10));
        final int month=Integer.valueOf(timegas.substring(3,5));
        final int date=Integer.valueOf(timegas.substring(0,2));

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String inputString1 = lastvalue[0].getDate()+" "+lastvalue[0].getMonth()+" "+lastvalue[0].getYear();
        String inputString2 = date+" "+month+" "+year;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = (TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.HOURS)/24)*24;

            if(diff==0){
                diff=Integer.valueOf(timegas.substring(11,13))-lastvalue[0].getTime();
            }
            if(diff==0){
                diff=1;
            }
            obj.setBill((obj.getRide()*15*24)/diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }





        return  obj;

    }

    public void getprevoiusdata(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("GAS"+pathway).child("LASTVALUE");

        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database1.getReference("GAS"+pathway).child("CONSTANTS");



        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                constant[0] =dataSnapshot.getValue(gasconstants.class);
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

                lastvalue[0] =dataSnapshot.getValue(gaslastvalue.class);
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

    public void gettimedate(final TextView datetime){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        timegas = dateformat.format(c.getTime());
                    datetime.setText(timegas);
                    loader.setVisibility(View.GONE);}


    @Override
    public void onBackPressed() {
        startActivity(new Intent(gas_input.this, user.class));
        finish();
    }
}
