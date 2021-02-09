package com.example.human_safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class Sos extends AppCompatActivity {
    ImageView i1,i2,i4,i5;
    private ImageView i3;
    private TextView t1;
    DatabaseHandler mydDB;
    private final int REQUEST_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        i2=findViewById(R.id.contact);
        i3=findViewById(R.id.call);
        i4=findViewById(R.id.message);
        i5=findViewById(R.id.location);
        t1 = findViewById(R.id.textcall);

//
//        i1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
//                startActivity(intent);
//            }
//        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact();
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message();
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location();
            }
        });
    }

    private void location() {
    }

    private void message() {
    }

    private void call() {

        String number= t1.getText().toString();
        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(Sos.this, CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Sos.this,new String[]{CALL_PHONE},REQUEST_CALL);
            }
            else
                {
                    String s = "tell:"+number;
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(s)));
                }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CALL){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                call();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openContact() {
    }
}