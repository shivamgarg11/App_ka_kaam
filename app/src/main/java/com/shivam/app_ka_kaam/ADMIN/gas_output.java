package com.shivam.app_ka_kaam.ADMIN;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class gas_output extends AppCompatActivity {
    int it = 0;

    String pathway = "";
    final String[] gasDownload = new String[]{"Yearly", "Monthly", "Date Range"};
    int selected = gasDownload.length - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_output);

        pathway = getIntent().getStringExtra("path");
        TextView path = findViewById(R.id.path);
        path.setText("ADMIN/GAS/" + pathway);


        //////////////////////////////////
        Button setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            String[] arr = {"CHANGE CONSTANTS", "CHANGE RANGE", "PASSWORD RESET"};
            int selected = arr.length - 1;

            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(gas_output.this)
                        .setIcon(R.drawable.logoo)
                        .setTitle("              SETTING")
                        .setSingleChoiceItems(arr, arr.length - 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selected == 0) {
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
        //////////////////////////////////

        Button goback = findViewById(R.id.goback);
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
                selected = 0;
                AlertDialog dialog = new AlertDialog.Builder(gas_output.this)
                        .setTitle("")
                        .setSingleChoiceItems(gasDownload, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(gas_output.this, "You selected " + gasDownload[selected], Toast.LENGTH_SHORT).show();
                                if (selected == 0) {
                                    dialog.dismiss();
                                    selYear();
                                } else if (selected == 1) {
                                    dialog.dismiss();
                                    selMonth();
                                } else {
                                    dialog.dismiss();
                                    selRange();
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

        final String[] date = {""};


        Button summary = findViewById(R.id.summary);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(gas_output.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 9)
                            date[0] = "" + year + "0" + (monthOfYear + 1) + dayOfMonth;
                        else
                            date[0] = "" + year + (monthOfYear + 1) + dayOfMonth;
                        Intent i = new Intent(gas_output.this, gassummary.class);
                        i.putExtra("DATE", date[0]);
                        startActivity(i);
                        finish();
                    }
                }, 2019, 01, 01).show();
            }
        });

    }


    public void changeconstant() {
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
        final EditText c5 = (EditText) changeconstant
                .findViewById(R.id.c5);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final String strc1 = c1.getText().toString() + "";
                                final String strc2 = c2.getText().toString() + "";
                                final String strc3 = c3.getText().toString() + "";
                                final String strc4 = c4.getText().toString() + "";
                                final String strc5 = c5.getText().toString() + "";

                                if (strc1.length() == 0 || strc2.length() == 0 || strc3.length() == 0 || strc4.length() == 0 || strc5.length() == 0) {
                                    FancyToast.makeText(gas_output.this, "INVALID INPUTS", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                } else {
                                    final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef1 = database1.getReference("GAS" + pathway).child("CONSTANTS");
                                    gasconstants con = new gasconstants(Double.valueOf(strc1), Double.valueOf(strc2), Double.valueOf(strc3), Double.valueOf(strc4), Double.valueOf(strc5));
                                    myRef1.setValue(con);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database1.getReference("GAS" + pathway).child("CONSTANTS");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gasconstants con = dataSnapshot.getValue(gasconstants.class);
                c1.setText(con.getC1() + "");
                c2.setText(con.getC2() + "");
                c3.setText(con.getC3() + "");
                c4.setText(con.getC4() + "");
                c5.setText(con.getC5() + "");
                alertDialog.show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }


    int year = Calendar.getInstance().get(Calendar.YEAR);
    public ArrayList<String> arr = new ArrayList<>();
    String writeCSV = "";

    public void selYear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(gas_output.this);
        View view = getLayoutInflater().inflate(R.layout.spinner_dialog, null);
        builder.setTitle("Select Year to View Report");
        final Spinner spinner = view.findViewById(R.id.spinner);
        for (int i = year; i != year - 10; i--) {
            arr.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(gas_output.this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final FirebaseDatabase database1 = FirebaseDatabase.getInstance();

                writeCSV += "year,month,date,bill,difference,input,mmbto,ride,scm,time,\n";
                final DatabaseReference myRef1 = database1.getReference("GASMUKTA").child(String.valueOf(spinner.getSelectedItem().toString()));
                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot yearIter : dataSnapshot.getChildren()) {
                                Log.d("201999", "onDataChange: " + yearIter.getKey() + "End");
                                writeCSV += spinner.getSelectedItem().toString() + ",";
                                writeCSV += yearIter.getKey() + ",";
                                for (DataSnapshot monthIter : yearIter.getChildren()) {
                                    writeCSV += monthIter.getKey() + ",";
                                    for (DataSnapshot dayIter : monthIter.getChildren()) {
//                                            writeCSV += dayIter.
                                        writeCSV += dayIter.getValue() + ",";
                                    }
                                    writeCSV += "\n";
                                }
                            }
                            Log.d("writecsv", "onDataChange: " + writeCSV);

                        } else {
                            Toast.makeText(gas_output.this, "Entry Doesn't Exist", Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        csvPart(csvWrite, "Year");
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        Log.d("Years", "selYear: " + arr.get(0) + arr.get(4));

    }

    public void selMonth() {
        Toast.makeText(this, "" + gasDownload[selected], Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(gas_output.this);
        View view = getLayoutInflater().inflate(R.layout.two_spinner_dialog, null);
        builder.setTitle("Select Year and Month to View Report");
        final Spinner spinner = view.findViewById(R.id.spinner_one);
        for (int i = year; i != year - 10; i--) {
            arr.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(gas_output.this, android.R.layout.simple_spinner_item, arr);
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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(gas_output.this, android.R.layout.simple_spinner_item, arrayList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(gas_output.this, spinner.getSelectedItem().toString() + spinner2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                final DatabaseReference myRef1 = database1.getReference("GASMUKTA").child(String.valueOf(spinner.getSelectedItem().toString())).child(String.valueOf(spinner2.getSelectedItemPosition() + 1).toString());
                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String csvWrite = "Year,Month,date,bill,difference,input,mmbto,ride,scm\n";
                            csvWrite += spinner.getSelectedItem().toString() + ",";
                            csvWrite += spinner2.getSelectedItem().toString() + ",";
                            for (DataSnapshot datesIter : dataSnapshot.getChildren()) {
                                csvWrite += datesIter.getKey() + ",";
                                for (DataSnapshot dayIter : datesIter.getChildren()) {
                                    csvWrite += dayIter.getValue() + ",";
                                    Log.d("dateIter", "onDataChange: " + dayIter.getValue());

                                }
                                csvWrite += "\n";

                            }

                            Log.d("csvWrite", "onDataChange: " + csvWrite);
                            csvPart(csvWrite, "Month");

                        } else
                            Toast.makeText(gas_output.this, "Entry Doesn't Exist", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Log.d("DATEtime", spinner.getSelectedItem().toString() + " " + (spinner2.getSelectedItemPosition() + 1));

            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    String dateStart = "";

    String dateEnd = "";
    String csvWrite = "Year,Month,date,bill,difference,input,mmbto,ride,scm\n";

    public void selRange() {
        Toast.makeText(this, "" + gasDownload[selected], Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(gas_output.this);
        View view = getLayoutInflater().inflate(R.layout.date_range, null);
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
                        if (monthOfYear < 9)
                            dateStart = dayOfMonth + "/0" + String.valueOf(Integer.valueOf(monthOfYear + 1)) + "/" + year;
                        else
                            dateStart = dayOfMonth + "/" + String.valueOf(Integer.valueOf(monthOfYear + 1)) + "/" + year;
                        tvDateStart.setText(String.valueOf(dayOfMonth) + "/0" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));

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
                        if (monthOfYear < 9)
                            dateEnd = dayOfMonth + "/0" + String.valueOf(Integer.valueOf(monthOfYear + 1)) + "/" + year;
                        else
                            dateEnd = dayOfMonth + "/" + String.valueOf(Integer.valueOf(monthOfYear + 1)) + "/" + year;
                        tvDateEnd.setText(String.valueOf(dayOfMonth) + "/0" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));

                    }
                }, 2019, 01, 01).show();


            }
        });


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Date date2, date1;


                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateStart);

                    date2 = new SimpleDateFormat("dd/MM/yyyy").parse(dateEnd);
                    Log.d("Dates", "onClick: " + date1.toString() + date2.toString());

                    if (date1.after(date2)) {
                        Toast.makeText(gas_output.this, "Please Check the dates!", Toast.LENGTH_SHORT).show();
                    } else {
                        Calendar calendar1 = new GregorianCalendar();
                        calendar1.setTime(date1);
                        Calendar calendar2 = new GregorianCalendar();
                        calendar2.setTime(date2);
                        final int year1 = calendar1.get(Calendar.YEAR);
                        int month1 = calendar1.get(Calendar.MONTH) + 1;
                        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);


                        final int year2 = calendar2.get(Calendar.YEAR);
                        int month2 = calendar2.get(Calendar.MONTH) + 1;
                        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef1 = database1.getReference("GASMUKTA");
                        myRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {

                                    for (it = year1; it <= year2 + 1; it++) {

                                        Log.d("Years", String.valueOf(it) + "onDataChange: ");
                                        DatabaseReference myRef2 = database1.getReference("GASMUKTA").child(String.valueOf(it));
                                        myRef2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot yearIter : dataSnapshot.getChildren()) {
                                                    Log.d("RangeDate", "onDataChange: " + yearIter.getKey().toString());
                                                    csvWrite += String.valueOf(dataSnapshot.getKey()) + ",";
                                                    csvWrite += yearIter.getKey() + ",";
                                                    for (DataSnapshot monthIter : yearIter.getChildren()) {
                                                        csvWrite += monthIter.getKey() + ",";
                                                        Log.d("monthIIII", "onDataChange: " + monthIter.getKey());
                                                        Log.d("monthIIII", "onDataChange: " + monthIter.getValue());

                                                        for (DataSnapshot dayIter : monthIter.getChildren()) {
                                                            csvWrite += dayIter.getValue() + ",";
                                                            Log.d("dayIIII", "onDataChange: " + dayIter.getKey());
                                                            Log.d("dayIIII", "onDataChange: " + dayIter.getValue());


                                                        }
                                                        csvWrite += "\n";
                                                        csvWrite += String.valueOf(dataSnapshot.getKey()) + ",";
                                                    }
                                                }
                                                csvWrite += "\n";

                                                Log.d("CSV", "selRange: " + csvWrite);
                                                csvPart(csvWrite, "Range");
                                            }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                } else
                                    Toast.makeText(gas_output.this, "Entry Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                } catch (ParseException e) {
                    Toast.makeText(gas_output.this, "Dates could not be parsed, Please try again!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    Map<String, String> value;

    public String csvPart(String data, String name) {

        //        String a = "1,2,4,5,6";
        String filePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name + ".csv";
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
            bw.write(data);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Check for Success by reading

//        String b = "";
//        BufferedReader br = null;
//        try {
//            String sCurrentLine;
//            br = new BufferedReader(new FileReader("/storage/emulated/0/Download/employee.csv"));
//            while ((sCurrentLine = br.readLine()) != null) {
//                b = b + sCurrentLine;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try {
//                if (br != null) br.close();
//                Log.d("MessageHereRead", "onCreate: " + b);
//            } catch (IOException ex) {
//                Log.d("MainActivity.java", "csvPart: Error");
//            }
//        }
        return "";
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(gas_output.this, admin.class));
        finish();
    }


}