package com.shivam.app_ka_kaam.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.MainActivity;
import com.shivam.app_ka_kaam.R;

public class user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ////////////getting from firebase/////////////////////////
        final String[] oilarray={"MAIN TANK","TUNNEL TANK"};
        final String[] gasarray={"MUKTA"};
        final String[] electricityarray={"MUKTA","MEENA"};
        //////////////////////////////////////////////////////////





        final LinearLayout oil=findViewById(R.id.useroil);
        LinearLayout gas=findViewById(R.id.usergas);
        LinearLayout electricity=findViewById(R.id.userelectricity);

        oil.setOnClickListener(new View.OnClickListener() {

            int selected=oilarray.length-1;

            @Override
            public void onClick(View v) {

                AlertDialog dialog=new AlertDialog.Builder(user.this)
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
                                FancyToast.makeText(user.this, "You selected " + oilarray[selected], FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                Intent i=new Intent(user.this,oil_input.class);
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

                AlertDialog dialog=new AlertDialog.Builder(user.this)
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
                                FancyToast.makeText(user.this, "You selected " + gasarray[selected], FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                Intent i=new Intent(user.this,gas_input.class);
                                i.putExtra("path",gasarray[selected]);
                                startActivity(i);
                                finish();                            }
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

                AlertDialog dialog=new AlertDialog.Builder(user.this)
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
                                FancyToast.makeText(user.this, "You selected " + electricityarray[selected], FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                Intent i=new Intent(user.this,electricity_input.class);
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
        startActivity(new Intent(user.this, MainActivity.class));
        finish();
    }
}
