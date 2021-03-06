package com.shivam.app_ka_kaam.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.ADMIN.admin;
import com.shivam.app_ka_kaam.Java_objects.electricity_object;
import com.shivam.app_ka_kaam.Java_objects.electricityconstants;
import com.shivam.app_ka_kaam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class electricitysummaryfrag extends Fragment {

    String strdate="";
    String pathway="";
    Context context;
double from,to;

    public electricitysummaryfrag() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public electricitysummaryfrag(String date, Context context,String pathway) {
        this.strdate=date;
        this.context=context;
        this.pathway=pathway;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_electricitysummaryfrag, container, false);



        int year=Integer.valueOf(strdate.substring(0,4));
        int month=Integer.valueOf(strdate.substring(4,6));
        int date=Integer.valueOf(strdate.substring(6));
        TextView datetime=rootview.findViewById(R.id.datetime);
        datetime.setText(date+"/"+month+"/"+year);


        final TextView KWH=rootview.findViewById(R.id.KWH);
        final TextView KVAH=rootview.findViewById(R.id.KVAH);
        final TextView diffKWH=rootview.findViewById(R.id.diffKWH);
        final TextView diffKVAH=rootview.findViewById(R.id.diffKVAH);
        final TextView CALPF=rootview.findViewById(R.id.CALPF);
        final TextView MPF=rootview.findViewById(R.id.MPF);
        final TextView PF=rootview.findViewById(R.id.PF);
        final TextView amount11=rootview.findViewById(R.id.amount11);
        final TextView amount1=rootview.findViewById(R.id.amount1);
        final TextView amount21=rootview.findViewById(R.id.amount21);
        final TextView amount2=rootview.findViewById(R.id.amount2);



        final FirebaseDatabase database11 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef11 = database11.getReference("ELECTRICITY"+pathway).child("RANGE");
        myRef11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                from =dataSnapshot.child("FROM").getValue(Double.class);
                to =dataSnapshot.child("TO").getValue(Double.class);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });




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
                    FancyToast.makeText(context,"ERROR HAS OCCURED", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    startActivity(new Intent(context,admin.class));
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
                    diffKWH.setText((float)obj.getDiffkwh()+"");
                    diffKVAH.setText((float)obj.getDiffkvah()+"");
                    KWH.setText((float)obj.getKwh()+"");
                    KVAH.setText((float)obj.getKvah()+"");
                    CALPF.setText((float)obj.getCal_pf()+"");
                    MPF.setText((float)obj.getMpf()+"");
                    PF.setText((float)obj.getPpf()+"");
                    amount1.setText((float)obj.getAmount1()+"");
                    amount2.setText((float)obj.getAmount2()+"");

                    if((float)obj.getCal_pf()>=from&&(float)obj.getCal_pf()<=to){
                        CALPF.setTextColor(Color.GREEN);
                        amount2.setTextColor(Color.GREEN);
                    }else{
                        CALPF.setTextColor(Color.RED);
                        amount2.setTextColor(Color.RED);
                    }

                }else{
                    diffKWH.setText("NULL");
                    diffKVAH.setText("NULL");
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


        return rootview;
    }

}
