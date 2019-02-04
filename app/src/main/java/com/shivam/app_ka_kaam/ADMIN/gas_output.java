package com.shivam.app_ka_kaam.ADMIN;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shivam.app_ka_kaam.R;

import java.util.ArrayList;
import java.util.Calendar;

public class gas_output extends AppCompatActivity {

    final String[] gasDownload = new String[]{"Yearly","Monthly","Week"};
    int selected=gasDownload.length-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_output);

        String pathway=getIntent().getStringExtra("path");
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






        Button download = findViewById(R.id.download);

        download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selected=0;
                AlertDialog dialog=new AlertDialog.Builder(gas_output.this)
                        .setTitle("")
                        .setSingleChoiceItems(gasDownload,0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(gas_output.this, "You selected " + gasDownload[selected], Toast.LENGTH_SHORT).show();
                                if(selected == 0)
                                {
                                    dialog.dismiss();
                                    selYear();
                                }
                                else if(selected == 1){selMonth();}

                                else{selWeek();}
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

    }

    int year = Calendar.getInstance().get(Calendar.YEAR);
    public ArrayList<String> arr = new ArrayList<>();

    public void selYear(){
        Toast.makeText(this, "" + gasDownload[selected] , Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(gas_output.this);
        View view = getLayoutInflater().inflate(R.layout.spinner_dialog,null);
        builder.setTitle("Select Year to View Report");
        final Spinner spinner = view.findViewById(R.id.spinner);
        for(int i = year;i!=year-10;i--){
            arr.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(gas_output.this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(gas_output.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        Log.d("Years", "selYear: " + arr.get(0) + arr.get(4));

    }

    public void selMonth(){
        Toast.makeText(this, "" + gasDownload[selected] , Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(gas_output.this);
        View view = getLayoutInflater().inflate(R.layout.two_spinner_dialog,null);
        builder.setTitle("Select Year and Month to View Report");
        final Spinner spinner = view.findViewById(R.id.spinner_one);
        for(int i = year;i!=year-10;i--){
            arr.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(gas_output.this,android.R.layout.simple_spinner_item,arr);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        final Spinner spinner2 = view.findViewById(R.id.spinner_two);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("January");
        arrayList.add("February");
        arrayList.add("March");
        arrayList.add("April");
        arrayList.add("May");
        arrayList.add("June");
        arrayList.add("July");
        arrayList.add("August");
        arrayList.add("September");
        arrayList.add("October");
        arrayList.add("November");
        arrayList.add("December");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(gas_output.this,android.R.layout.simple_spinner_item,arrayList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(gas_output.this, spinner.getSelectedItem().toString() + spinner2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void selWeek(){
        Toast.makeText(this, "" + gasDownload[selected] , Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(gas_output.this, admin.class));
        finish();
    }
}
