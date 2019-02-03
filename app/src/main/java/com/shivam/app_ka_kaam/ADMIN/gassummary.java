package com.shivam.app_ka_kaam.ADMIN;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivam.app_ka_kaam.Java_objects.gas_object;
import com.shivam.app_ka_kaam.R;

public class gassummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gassummary);

        Button goback=findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gassummary.this, admin.class));
                finish();
            }
        });

        String datestr= getIntent().getStringExtra("DATE");

        int year=Integer.valueOf(datestr.substring(0,4));
        int month=Integer.valueOf(datestr.substring(4,6));
        int date=Integer.valueOf(datestr.substring(6));
        TextView datetime=findViewById(R.id.datetime);
        datetime.setText(date+" "+month+" "+year);

        final TextView input=findViewById(R.id.input);
        final TextView difference=findViewById(R.id.difference);
        final TextView scm=findViewById(R.id.scm);
        final TextView mmbto=findViewById(R.id.mmbto);
        final TextView ride=findViewById(R.id.ride);
        final TextView bill=findViewById(R.id.bill);


        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database1.getReference("GASMUKTA").child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(date));

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
               gas_object obj=dataSnapshot.getValue(gas_object.class);
                   input.setText((float)obj.getInput() + "");
                   difference.setText((float)obj.getDifference() + "");
                   scm.setText((float)obj.getScm() + "");
                   mmbto.setText((float)obj.getMmbto() + "");
                   ride.setText((float)obj.getRide() + "");
                   bill.setText((float)obj.getBill() + "");
               }else{
                    input.setText("NULL");
                    difference.setText("NULL");
                    scm.setText("NULL");
                    mmbto.setText("NULL");
                    ride.setText("NULL");
                    bill.setText("NULL");
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
        startActivity(new Intent(gassummary.this,admin.class));
        finish();
    }
}
