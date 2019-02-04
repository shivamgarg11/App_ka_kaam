package com.shivam.app_ka_kaam;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shivam.app_ka_kaam.ADMIN.admin;
import com.shivam.app_ka_kaam.User.user;
import com.shivam.app_ka_kaam.sampleUtil.Constants;
import com.shivam.app_ka_kaam.sampleUtil.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!checkPermission()){
            requestPermission();
        }
        else
        {
            afterPerm();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
            switch (requestCode) {
                case PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0) {

                        boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        if (locationAccepted)  afterPerm();

                        else {

                            FancyToast.makeText(this, "Permission Denied!", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                    showMessageOKCancel("You need to allow access to the permissions",
                                            new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE},
                                                                PERMISSION_REQUEST_CODE);
                                                    }
                                                }
                                            });
                                    return;
                                }
                            }

                        }
                    }


                    break;
            }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void afterPerm()
    {
        fetchData();
        ImageView adminbtn=findViewById(R.id.admin);
        ImageView userbtn=findViewById(R.id.user);


        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,admin.class));
                finish();
            }
        });




        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,user.class));
                finish();
            }
        });
        //////////////////////////////////////////////////////////

//        String s = String.valueOf(FirebaseInstanceId.getInstance().getToken());
//        Log.d("TokenHere", "onCreate: "+s);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID,Constants.CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription(Constants.CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {100,200,300,400,500,400,300,200,400});
            notificationManager.createNotificationChannel(channel);
        }
    }

    Map<String,String> value;

    public void fetchData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference gasRef = database.getReference();
        DatabaseReference electMeena = database.getReference("ELECTRICITYMEENA");
        DatabaseReference electMukta = database.getReference("ELECTRICITYMUKTA");

        electMukta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,String> value = (Map<String,String>)dataSnapshot.getValue();
                Log.d("FetchedElectMukta", "Value is: " + value.toString());
                Log.d("FetchedELectMukta", "onDataChange: "+ Arrays.toString(value.entrySet().toArray()));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        electMeena.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,String> value = (Map<String,String>)dataSnapshot.getValue();
                Log.d("FetchedElectMeena", "Value is: " + value.toString());
                Log.d("FetchedELectMeena", "onDataChange: "+ Arrays.toString(value.entrySet().toArray()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        gasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                value = (Map<String,String>)dataSnapshot.getValue();
                Log.d("FetchedGas", "Value is: " + value.toString());
                Log.d("Fetched", "onDataChange: "+ Arrays.toString(value.entrySet().toArray()) + csvPart());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

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
}
