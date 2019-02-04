package com.shivam.app_ka_kaam.User;

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
import android.widget.Toast;

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
import java.util.Calendar;
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
        gettimedate(datetime);



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



    public  void onclickbutton() {

        EditText electricityinput1 = findViewById(R.id.electricityinput1);
        String datastr1=electricityinput1.getText().toString()+"";


        EditText electricityinput2 = findViewById(R.id.electricityinput2);
        String datastr2=electricityinput2.getText().toString()+"";


        EditText electricityinput3 = findViewById(R.id.electricityinput3);
        String datastr3=electricityinput3.getText().toString()+"";


        EditText electricityinput4 = findViewById(R.id.electricityinput4);
        String datastr4=electricityinput4.getText().toString()+"";



        if (datastr1.length() == 0||datastr2.length()==0||datastr3.length()==0||datastr4.length()==0) {
            FancyToast.makeText(this, "PLEASE ENTER THE INPUT ", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

        } else {
            FancyToast.makeText(this, "WAIT WHILE WE ARE MAKING EVERYTHING READY ", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            loader.setVisibility(View.VISIBLE);
            final double data1 = Double.valueOf(datastr1);
            final double data2 = Double.valueOf(datastr2);
            final double data3 = Double.valueOf(datastr3);
            final double data4 = Double.valueOf(datastr4);

            alertDialog = new AlertDialog.Builder(electricity_input.this)
                    .setTitle("CONFIRMATION:")
                    .setMessage("\nDATA 1: " + data1 + "\n\n" + "DATA 2: " + data2 + "\n\n" + "DATA 3: " + data3 + "\n\n" + "DATA 4: " + data4)
                    .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            //writing value
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference("ELECTRICITY" + pathway).child("ENTERIES").child(timeelectricity.substring(0,10));


                            //Writing lastvalue
                            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef1 = database1.getReference("ELECTRICITY" + pathway).child("LASTVALUE");

                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        electricity_object obj = insertvalues(data1, data2, data3, data4);
                                        myRef.setValue(obj);
                                        FancyToast.makeText(electricity_input.this, "THANK YOU FOR UPDATING \n\n YOU HAVE BEEN LOGGED OUT", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                        electricitylastvalue obj1 = new electricitylastvalue(timeelectricity, data1, data2);
                                        myRef1.setValue(obj1);

                                    } else{
                                        FancyToast.makeText(electricity_input.this,"YOU HAVE ALREADY ENTERED THE DATA", Toast.LENGTH_SHORT,FancyToast.INFO,false).show();

                                    }

                                    startActivity(new Intent(electricity_input.this, MainActivity.class));
                                    finish();
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

            getprevoiusdata();

        }
    }


    public  electricity_object insertvalues(double input1,double input2,double input3,double input4){
        electricity_object obj = new electricity_object();

        obj.setKwh(input1);
        obj.setKvah(input2);
        obj.setMpf(input3);
        obj.setPpf(input4);
        obj.setCal_pf((input1-lastvalue[0].getKwh())/(input2-lastvalue[0].getKvah()));
        obj.setDiffkwh(input1-lastvalue[0].getKwh());
        obj.setDiffkvah(input2-lastvalue[0].getKvah());

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy HH:mm");
        String inputString1 = lastvalue[0].getDate();
        String inputString2 = timeelectricity;

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = TimeUnit.MILLISECONDS.toHours(date2.getTime() - date1.getTime());
            if(diff==0)
                diff=1;

            obj.setAmount1(obj.getCal_pf()*constant[0].getC1()*constant[0].getC3()*15);
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


        //
        myRef.setValue(new electricitylastvalue(timeelectricity,12,12));
        //


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



    public  void gettimedate(final TextView datetime){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MM yyyy HH:mm");
        timeelectricity = dateformat.format(c.getTime());
                    datetime.setText(timeelectricity);
                    loader.setVisibility(View.GONE);}


    @Override
    public void onBackPressed() {
        startActivity(new Intent(electricity_input.this, user.class));
        finish();
    }

}






