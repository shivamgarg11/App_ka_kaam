package com.shivam.app_ka_kaam.ADMIN;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.shivam.app_ka_kaam.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class oil_output extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_output);

        String pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("ADMIN/OIL/"+pathway);
    }



    public static String csvPart(String data, String name) {

        //        String a = "1,2,4,5,6";
        String filePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "App_Ka_Kaam/" + name + ".csv";
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
        return "";
    }

    public void sendNotif(String path)
    {

//        String path = android.os.Environment.getExternalStorageDirectory() + "/" + "App_Ka_Kaam/";
        Uri selectedUri = Uri.parse(android.os.Environment.getExternalStorageDirectory() + "/" + "App_Ka_Kaam/");
        Intent intent = new Intent(Intent.ACTION_VIEW,selectedUri);

        intent.setDataAndType(selectedUri, "resource/folder");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(oil_output.this, 0, intent, 0);
        createNotificationChannel();

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "1")
                .setSmallIcon(R.drawable.logoo)
                .setContentTitle("File Downloaded")
                .setContentText("Tap to View")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(uri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0, mBuilder.build());
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "Desc";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(oil_output.this, admin.class));
        finish();
    }
}
