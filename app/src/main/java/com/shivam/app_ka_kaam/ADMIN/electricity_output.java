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
import com.shivam.app_ka_kaam.Fragments.electricitysummaryfrag;
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
                                   // changeconstant();
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



    @Override
    public void onBackPressed() {
        startActivity(new Intent(electricity_output.this, admin.class));
        finish();
    }
}
