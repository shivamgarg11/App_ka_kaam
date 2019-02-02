package com.shivam.app_ka_kaam.ADMIN;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.shivam.app_ka_kaam.R;
import com.shivam.app_ka_kaam.User.electricity_input;
import com.shivam.app_ka_kaam.User.user;

public class electricity_output extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_output);


        String pathway=getIntent().getStringExtra("path");
        TextView path=findViewById(R.id.path);
        path.setText("ADMIN/ELECTRICITY/"+pathway);


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(electricity_output.this, admin.class));
        finish();
    }
}
