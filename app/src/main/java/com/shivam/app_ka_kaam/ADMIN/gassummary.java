package com.shivam.app_ka_kaam.ADMIN;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                   input.setText(obj.getInput() + "");
                   difference.setText(obj.getDifference() + "");
                   scm.setText(obj.getScm() + "");
                   mmbto.setText(obj.getMmbto() + "");
                   ride.setText(obj.getRide() + "");
                   bill.setText(obj.getBill() + "");
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
