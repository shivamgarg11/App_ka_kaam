package com.shivam.app_ka_kaam.ADMIN;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.Java_objects.electricityconstants;
import com.shivam.app_ka_kaam.Java_objects.electricity_object;
import com.shivam.app_ka_kaam.R;

public class electricitysummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricitysummary);


        Button goback=findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(electricitysummary.this, admin.class));
                finish();
            }
        });

String pathway=getIntent().getStringExtra("pathway");


        String datestr= getIntent().getStringExtra("DATE");

        int year=Integer.valueOf(datestr.substring(0,4));
        int month=Integer.valueOf(datestr.substring(4,6));
        int date=Integer.valueOf(datestr.substring(6));
        TextView datetime=findViewById(R.id.datetime);
        datetime.setText(date+" "+month+" "+year);


        final TextView KWH=findViewById(R.id.KWH);
        final TextView KVAH=findViewById(R.id.KVAH);
        final TextView CALPF=findViewById(R.id.CALPF);
        final TextView MPF=findViewById(R.id.MPF);
        final TextView PF=findViewById(R.id.PF);
        final TextView amount11=findViewById(R.id.amount11);
        final TextView amount1=findViewById(R.id.amount1);
        final TextView amount21=findViewById(R.id.amount21);
        final TextView amount2=findViewById(R.id.amount2);


        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database1.getReference("ELECTRICITY"+pathway).child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(date));

        final DatabaseReference myRef2 = database1.getReference("ELECTRICITY"+pathway).child("CONSTANTS");


        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
               electricityconstants con=dataSnapshot.getValue(electricityconstants.class);
               amount11.setText("AMOUNT@"+con.getC1()+" :");
               amount21.setText("AMOUNT@"+con.getC2()+" :");
                }else{
                    FancyToast.makeText(electricitysummary.this,"ERROR HAS OCCURED", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    startActivity(new Intent(electricitysummary.this,admin.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
         electricity_object obj=dataSnapshot.getValue(electricity_object.class);

                    KWH.setText((float)obj.getKwh()+"");
                    KVAH.setText((float)obj.getKvah()+"");
                    CALPF.setText((float)obj.getCal_pf()+"");
                    MPF.setText((float)obj.getMpf()+"");
                    PF.setText((float)obj.getPpf()+"");
                    amount1.setText((float)obj.getAmount1()+"");
                    amount2.setText((float)obj.getAmount2()+"");

                }else{
                    KWH.setText("NULL");
                    KVAH.setText("NULL");
                    CALPF.setText("NULL");
                    MPF.setText("NULL");
                    PF.setText("NULL");
                    amount1.setText("NULL");
                    amount2.setText("NULL");
                }
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
        startActivity(new Intent(electricitysummary.this,admin.class));
        finish();
    }
}
