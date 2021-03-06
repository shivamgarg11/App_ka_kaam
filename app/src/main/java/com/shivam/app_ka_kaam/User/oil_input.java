package com.shivam.app_ka_kaam.User;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shivam.app_ka_kaam.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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



        Button close =findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(oil_input.this,user.class));
                finish();
            }
        });


        Button done =findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText oilinput1=findViewById(R.id.oilinput1);
                 String data1=oilinput1.getText().toString();

                EditText oilinput2=findViewById(R.id.oilinput2);
                 String data2=oilinput2.getText().toString();

                EditText oilinput3=findViewById(R.id.oilinput3);
                String data3=oilinput3.getText().toString();

                EditText oilinput4=findViewById(R.id.oilinput4);
                String data4=oilinput4.getText().toString();

                final AlertDialog alertDialog = new AlertDialog.Builder(oil_input.this)
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

                    }
                }).create();
               alertDialog.show();

            }
        });

    }



    public static void gettimedate(Context context, final TextView datetime){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        timeoil = dateformat.format(c.getTime());
        datetime.setText(timeoil);

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(oil_input.this, user.class));
        finish();
    }

}


