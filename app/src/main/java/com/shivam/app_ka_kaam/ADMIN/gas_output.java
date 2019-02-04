package com.shivam.app_ka_kaam.ADMIN;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shivam.app_ka_kaam.R;
import com.shivam.app_ka_kaam.sampleUtil.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class gas_output extends AppCompatActivity {

    final String[] gasDownload = new String[]{"Yearly","Monthly","Date Range"};
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
                                else if(selected == 1){dialog.dismiss();selMonth();}

                                else{dialog.dismiss();selWeek();}
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
    String writeCSV="";

    public void selYear(){
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
                final FirebaseDatabase database1 = FirebaseDatabase.getInstance();

                writeCSV += "bill,difference,input,mmbto,ride,scm,time\n";
                final DatabaseReference myRef1 = database1.getReference("GASMUKTA").child(String.valueOf(spinner.getSelectedItem().toString()));
                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for(DataSnapshot yearIter : dataSnapshot.getChildren())
                            {
                                Log.d("201999", "onDataChange: " + yearIter.getKey() +"End");
//                                writeCSV += spinner.getSelectedItem().toString()+",";
//                                writeCSV += yearIter.getKey()+",";
                                for( DataSnapshot monthIter : yearIter.getChildren())
                                {
//                                    writeCSV += monthIter.getKey()+",";
                                    for(DataSnapshot dayIter: monthIter.getChildren())
                                    {
//                                            writeCSV += dayIter.
                                            writeCSV += dayIter.getValue()+",";
                                    }
                                    writeCSV+="\n";
                                }
                            }
                            Log.d("writecsv", "onDataChange: " + writeCSV);

                        }


                        else{
                            Toast.makeText(gas_output.this,"Entry Doesn't Exist",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
    String dateStart = "";
    String dateEnd = "";
    public void selWeek(){
        Toast.makeText(this, "" + gasDownload[selected] , Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(gas_output.this);
        View view = getLayoutInflater().inflate(R.layout.date_range,null);
        ImageView date1Im = view.findViewById(R.id.date_1_im);
        ImageView date2Im = view.findViewById(R.id.date_2_im);

        final TextView tvDateStart = view.findViewById(R.id.date_1_tv);
        final TextView tvDateEnd = view.findViewById(R.id.date_2_tv);
        date1Im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(gas_output.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear<9)
                            dateStart =""+year+"/0"+(monthOfYear+1)+"/"+dayOfMonth;
                        else
                            dateStart =+year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
                        tvDateStart.setText(String.valueOf(dayOfMonth) +"/"+ String.valueOf(monthOfYear) +"/"+ String.valueOf(year));

                    }
                }, 2019, 01, 01).show();


            }
        });

        date2Im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(gas_output.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear<9)
                            dateEnd =""+year+"/0"+(monthOfYear+1)+"/"+dayOfMonth;
                        else
                            dateEnd =""+year+"/"+(monthOfYear+1)+"/"+dayOfMonth;

                        tvDateEnd.setText(String.valueOf(dayOfMonth) +"/"+ String.valueOf(monthOfYear) +"/"+ String.valueOf(year));

                    }
                }, 2019, 01, 01).show();

            }
        });


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Log.d("DateRange", "onClick: " + sdf.parse(dateStart).before(sdf.parse(dateEnd)));
                    Log.d("DateRan", "onClick: " +dateStart + dateEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });




        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    Map<String,String> value;

    public String csvPart()
    {

        Employee emp1 = new Employee(1, "FirstName1", "LastName1", 10000);
        Employee emp2 = new Employee(2, "FirstName2", "LastName2", 20000);
        Employee emp3 = new Employee(3, "FirstName3", "LastName3", 30000);
        Employee emp4 = new Employee(4, "FirstName4", "LastName4", 40000);
        Employee emp5 = new Employee(5, "FirstName5", "LastName5", 50000);

        //Add Employee objects to a list
        List empList = new ArrayList();
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);
        empList.add(emp4);
        empList.add(emp5);


        String a = empList.get(0).toString();
        a = a + "\n" + empList.get(1).toString();

        //        String a = "1,2,4,5,6";
        Log.d("MessageFirst A", "onCreate: " + a);
        String filePath = "/storage/emulated/0/Download/employee.csv";
//        String
        try {
            String content = "Separe here integers by semi-colon";
            File file = new File(filePath);
            // if file doesnt exists, then create it
            try {
                if (!file.exists()) {
                    new File(file.getParent()).mkdirs();
                    file.createNewFile();
                }
            } catch (IOException e) {
                Log.e("", "Could not create file.", e);
                return "";
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value.toString());
            Log.d("MessageHere", a);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Check for Success by reading

        String b = "";
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader("/storage/emulated/0/Download/employee.csv"));
            while ((sCurrentLine = br.readLine()) != null) {
                b = b + sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (br != null) br.close();
                Log.d("MessageHereRead", "onCreate: " + b);
            } catch (IOException ex) {
                Log.d("MainActivity.java", "csvPart: Error");
            }
        }
        return "";
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(gas_output.this, admin.class));
        finish();
    }
}
