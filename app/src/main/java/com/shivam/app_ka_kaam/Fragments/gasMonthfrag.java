package com.shivam.app_ka_kaam.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivam.app_ka_kaam.Java_objects.gas_object;

import com.shivam.app_ka_kaam.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class gasMonthfrag extends Fragment {

    ArrayList<gas_object> dataarray=new ArrayList<>();
    String[] datastr;
    ArrayList<String>dates=new ArrayList<>();
Context context;
     int year;
     int month;
     double from,to;

    public gasMonthfrag() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public gasMonthfrag(Context context) {
        this.context=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview= inflater.inflate(R.layout.fragment_gas_monthfrag, container, false);


        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,0);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        final String strdate = dateformat.format(c.getTime());

         year=Integer.valueOf(strdate.substring(0,4));
         month=Integer.valueOf(strdate.substring(4,6));




        final FirebaseDatabase database11 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef11 = database11.getReference("GASMUKTA").child("RANGE");
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
        final DatabaseReference myRef1 = database1.getReference("GASMUKTA").child(String.valueOf(year)).child(String.valueOf(month));

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    dataarray.add(postSnapshot.getValue(gas_object.class));
                   dates.add(postSnapshot.getKey());
                }

                datastr=new String[dataarray.size()];
                for(int i=0;i<dataarray.size();i++) {
                    datastr[i]= dates.get(i)+"/" + month + "/" + year + "          " + dataarray.get(i).getInput() + "           " + (float) dataarray.get(i).getBill();
                    Log.e("TAGhvb", "onDataChange: "+datastr[i]);
                }
                //String[] users = { "Suresh Dasari", "Rohini Alavala", "Trishika Dasari", "Praveen Alavala", "Madav Sai"};

               // ArrayAdapter itemsAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1,datastr);

                ListView listView = (ListView) rootview.findViewById(R.id.gaslist);
                listView.setAdapter(new CustomListAdapter(context, dataarray));


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



        return rootview;
    }








    public class CustomListAdapter extends BaseAdapter {
        private ArrayList<gas_object> listData;
        private LayoutInflater layoutInflater;
        public CustomListAdapter(Context aContext, ArrayList<gas_object> listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }
        @Override
        public int getCount() {
            return listData.size();
        }
        @Override
        public gas_object getItem(int position) {
            return listData.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View v, ViewGroup vg) {
            ViewHolder holder;
            if (v == null) {
                v = layoutInflater.inflate(R.layout.gaslistitem, null);
                holder = new ViewHolder();
                holder.uName =  v.findViewById(R.id.date);
                holder.uDesignation =  v.findViewById(R.id.val1);
                holder.uLocation =  v.findViewById(R.id.val2);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            holder.uName.setText(dates.get(position)+"/"+month+"/"+year);
            holder.uDesignation.setText((float)listData.get(position).getInput()+"");
            holder.uLocation.setText((float)listData.get(position).getBill()+"");

            if((float)listData.get(position).getBill()>=from&&(float)listData.get(position).getBill()<=to){
                holder.uDesignation.setTextColor(Color.GREEN);
                holder.uLocation.setTextColor(Color.GREEN);
                holder.uName.setTextColor(Color.GREEN);
            }else{
                holder.uDesignation.setTextColor(Color.RED);
                holder.uLocation.setTextColor(Color.RED);
                holder.uName.setTextColor(Color.RED);
            }


            return v;
        }
         class ViewHolder {
            TextView uName;
            TextView uDesignation;
            TextView uLocation;
        }
    }





}
