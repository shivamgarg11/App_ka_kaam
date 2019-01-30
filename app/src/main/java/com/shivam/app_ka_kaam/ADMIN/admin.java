package com.shivam.app_ka_kaam.ADMIN;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.shivam.app_ka_kaam.MainActivity;
import com.shivam.app_ka_kaam.R;

public class admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        ////////////getting from firebase/////////////////////////
        final String[] oilarray={"MAIN TANK","TUNNEL TANK"};
        final String[] gasarray={"MUKTA"};
        final String[] electricityarray={"MUKTA","MEENA"};
        //////////////////////////////////////////////////////////





        LinearLayout oil=findViewById(R.id.useroil);
        LinearLayout gas=findViewById(R.id.usergas);
        LinearLayout electricity=findViewById(R.id.userelectricity);

        oil.setOnClickListener(new View.OnClickListener() {

            int selected=oilarray.length-1;

            @Override
            public void onClick(View v) {

                AlertDialog dialog=new AlertDialog.Builder(admin.this)
                        .setTitle("              OIL")
                        .setSingleChoiceItems(oilarray, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(admin.this, "You selected " + oilarray[selected], Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(admin.this,oil_output.class);
                                i.putExtra("path",oilarray[selected]);
                                startActivity(i);
                                finish();

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


        gas.setOnClickListener(new View.OnClickListener() {
            int selected=gasarray.length-1;
            @Override
            public void onClick(View v) {

                AlertDialog dialog=new AlertDialog.Builder(admin.this)
                        .setTitle("              GAS")
                        .setSingleChoiceItems(gasarray, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(admin.this, "You selected " + gasarray[selected], Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(admin.this,gas_output.class);
                                i.putExtra("path",gasarray[selected]);
                                startActivity(i);
                                finish();


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

        electricity.setOnClickListener(new View.OnClickListener() {
            int selected=electricityarray.length-1;
            @Override
            public void onClick(View v) {

                AlertDialog dialog=new AlertDialog.Builder(admin.this)
                        .setTitle("          ELECTRICITY")
                        .setSingleChoiceItems(electricityarray, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(admin.this, "You selected " + electricityarray[selected], Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(admin.this,electricity_output.class);
                                i.putExtra("path",electricityarray[selected]);
                                startActivity(i);
                                finish();


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

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(admin.this, MainActivity.class));
        finish();
    }
}


