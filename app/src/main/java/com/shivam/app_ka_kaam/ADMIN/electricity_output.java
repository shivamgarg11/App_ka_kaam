package com.shivam.app_ka_kaam.ADMIN;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.Fragments.electricitysummaryfrag;
import com.shivam.app_ka_kaam.Java_objects.electricityconstants;
import com.shivam.app_ka_kaam.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class electricity_output extends AppCompatActivity {

    String pathway="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_output);

        pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("ADMIN/ELECTRICITY/"+pathway);


        /////////////////////////////


        Button today=findViewById(R.id.today);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
                final String todaydate = dateformat.format(c.getTime());
                String passingstr =todaydate.substring(0,4)+todaydate.substring(5,7)+todaydate.substring(8);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                electricitysummaryfrag frag = new electricitysummaryfrag(passingstr,electricity_output.this,pathway);
                fragmentManager.beginTransaction().replace(R.id.frame, frag).commit();
            }
        });

        today.performClick();

        Button yesterday=findViewById(R.id.yesterday);
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE,-1);
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
                final String yesterdaydate = dateformat.format(c.getTime());
                String passingstr =yesterdaydate.substring(0,4)+yesterdaydate.substring(5,7)+yesterdaydate.substring(8);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                electricitysummaryfrag frag = new electricitysummaryfrag(passingstr,electricity_output.this,pathway);
                fragmentManager.beginTransaction().replace(R.id.frame, frag).commit();
            }
        });



        final String[] date = {""};


        Button summary=findViewById(R.id.summary);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(electricity_output.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear<9)
                            date[0] =""+year+"0"+(monthOfYear+1)+dayOfMonth;
                        else
                            date[0] =""+year+(monthOfYear+1)+dayOfMonth;
                        android.app.FragmentManager fragmentManager = getFragmentManager();
                        electricitysummaryfrag frag = new electricitysummaryfrag(date[0],electricity_output.this,pathway);
                        fragmentManager.beginTransaction().replace(R.id.frame, frag).commit();
                    }
                }, 2019, 01, 01).show();
            }
        });


        //////////////////////////////////
        Button setting =findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            String []arr={"CHANGE CONSTANTS","CHANGE RANGE","PASSWORD RESET"};
            int selected=arr.length-1;
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(electricity_output.this)
                        .setIcon(R.drawable.logoo)
                        .setTitle("              SETTING")
                        .setSingleChoiceItems(arr, arr.length-1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(selected==0){
                                    changeconstant();
                                }




                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
            }
        });




        Button goback=findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(electricity_output.this, admin.class));
                finish();
            }
        });




        //////////////////////////////////


    }


    public void changeconstant(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(electricity_output.this);
        View changeconstant = li.inflate(R.layout.setelectricityconstants, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                electricity_output.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(changeconstant);

        final EditText c1 = (EditText) changeconstant
                .findViewById(R.id.c1);
        final EditText c2 = (EditText) changeconstant
                .findViewById(R.id.c2);
        final EditText c3 = (EditText) changeconstant
                .findViewById(R.id.c3);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                final String strc1=c1.getText().toString()+"";
                                final String strc2=c2.getText().toString()+"";
                                final String strc3=c3.getText().toString()+"";

                                if(strc1.length()==0||strc2.length()==0||strc3.length()==0){
                                    FancyToast.makeText(electricity_output.this,"INVALID INPUTS", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }else{
                                    final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef1 = database1.getReference("ELECTRICITY"+pathway).child("CONSTANTS");
                                    electricityconstants con=new electricityconstants(Double.valueOf(strc1),Double.valueOf(strc2),Double.valueOf(strc3));
                                    myRef1.setValue(con);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database1.getReference("ELECTRICITY"+pathway).child("CONSTANTS");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                electricityconstants con =dataSnapshot.getValue(electricityconstants.class);
                c1.setText(con.getC1()+"");
                c2.setText(con.getC2()+"");
                c3.setText(con.getC3()+"");
                alertDialog.show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(electricity_output.this, admin.class));
        finish();
    }
}
