package com.shivam.app_ka_kaam;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView adminbtn=findViewById(R.id.admin);
        ImageView userbtn=findViewById(R.id.user);


        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


public void csvPart() {
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
    String filePath = getApplicationContext().getFilesDir().getPath().toString() + "/Employee.csv";
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
            return;
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(a);
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
        br = new BufferedReader(new FileReader(getApplicationContext().getFilesDir().getPath().toString() + "/Employee.csv"));
        while ((sCurrentLine = br.readLine()) != null) {
            b = b + sCurrentLine;
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (br != null) br.close();
            Log.d("MessageHereRead", "onCreate: " + b);
        } catch (IOException ex) {
            Log.d("MainActivity.java", "csvPart: Error");
            }
        }
    }
}
