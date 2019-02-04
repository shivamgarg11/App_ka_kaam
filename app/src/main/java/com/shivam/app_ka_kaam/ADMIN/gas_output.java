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
import com.shivam.app_ka_kaam.Java_objects.gasconstants;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.R;

public class gas_output extends AppCompatActivity {

    String pathway="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_output);

         pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("ADMIN/GAS/"+pathway);


        //////////////////////////////////

        //////////////////////////////////

        Button goback=findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gas_output.this, admin.class));
                finish();
            }
        });



        final String[] date = {""};
        Button summary=findViewById(R.id.summary);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(gas_output.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear<9)
                            date[0] =""+year+"0"+(monthOfYear+1)+dayOfMonth;
                        else
                            date[0] =""+year+(monthOfYear+1)+dayOfMonth;
Intent i=new Intent(gas_output.this,gassummary.class);
i.putExtra("DATE",date[0]);
startActivity(i);
finish();
                    }
                }, 2019, 01, 01).show();
            }
        });





Button setting =findViewById(R.id.setting);
setting.setOnClickListener(new View.OnClickListener() {
    String []arr={"CHANGE CONSTANTS","CHANGE RANGE","PASSWORD RESET"};
    int selected=arr.length-1;
    @Override
    public void onClick(View v) {
        AlertDialog dialog=new AlertDialog.Builder(gas_output.this)
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




    }



    public void changeconstant(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(gas_output.this);
        View changeconstant = li.inflate(R.layout.setgasconstants, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                gas_output.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(changeconstant);

        final EditText c1 = (EditText) changeconstant
                .findViewById(R.id.c1);
        final EditText c2 = (EditText) changeconstant
                .findViewById(R.id.c2);
        final EditText c3 = (EditText) changeconstant
                .findViewById(R.id.c3);
        final EditText c4 = (EditText) changeconstant
                .findViewById(R.id.c4);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                final String strc1=c1.getText().toString()+"";
                                final String strc2=c2.getText().toString()+"";
                                final String strc3=c3.getText().toString()+"";
                                final String strc4=c4.getText().toString()+"";

                                if(strc1.length()==0||strc2.length()==0||strc3.length()==0||strc4.length()==0){
                                    FancyToast.makeText(gas_output.this,"INVALID INPUTS",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }else{
                                    final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef1 = database1.getReference("GAS"+pathway).child("CONSTANTS");
                                    gasconstants con=new gasconstants(Double.valueOf(strc1),Double.valueOf(strc2),Double.valueOf(strc3),Double.valueOf(strc4),1000000);
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
        final DatabaseReference myRef1 = database1.getReference("GAS"+pathway).child("CONSTANTS");



        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gasconstants con =dataSnapshot.getValue(gasconstants.class);
                c1.setText(con.getC1()+"");
                c2.setText(con.getC2()+"");
                c3.setText(con.getC3()+"");
                c4.setText(con.getC4()+"");
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
        startActivity(new Intent(gas_output.this, admin.class));
        finish();
    }
}
