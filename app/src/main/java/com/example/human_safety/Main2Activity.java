package com.example.human_safety;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationSettingsRequest;
import com.skydoves.elasticviews.ElasticButton;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

public class Main2Activity extends AppCompatActivity {
    LinearLayout l1,l2,l3,l4,l5,sos;
    private FusedLocationProviderClient client;
    DatabaseHandler myDB;

    private final int REQUEST_CHECK_CODE = 8989;
    private LocationSettingsRequest.Builder builder;
    String x="", y="";
    EditText e1;
    private  static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        l1 = findViewById(R.id.contact);
        l2 = findViewById(R.id.instruction);
//        l3 = findViewById(R.id.login);
        l4 = findViewById(R.id.emergency);
        l5 = findViewById(R.id.sos);
        e1 = findViewById(R.id.e1);



        myDB = new DatabaseHandler (this);

        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.emergency_alaram);

        try {
            Cursor data = myDB.getListContents();
            data.moveToFirst();
            e1.setText(data.getString(1));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            onGPS();
        }
        else
            {
                startTrack();
            }

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Instructions.class);
                startActivity(intent);
            }
        });

//        l3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Login.class);
//                startActivity(intent);
//            }
//        });


        l4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mp.start();
                Toast.makeText(getApplicationContext(),"Panic Button Started",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadData();

                String msg = "I NEED HELP  \n LATITUDE:"+x+"LONGITUDE:"+y;

                sendSms(msg);
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sos.class);
                startActivity(intent);
            }
        });

    }

//    private void loadData()
//    {
//        myDB = new DatabaseHandler(this);
//        ArrayList<String> theList = new ArrayList<>( );
//        Cursor data = myDB.getListContents();
//        if (data.getCount()==0)
//        {
//            Toast.makeText(this,"No Content To Show",Toast.LENGTH_SHORT).show();
//        }
//        else
//            {
//                String msg = "I NEED HELP LATITUDE:"+x+"LONGITUDE:"+y;
//                String number = "";
//
//                while (data.moveToNext())
//                {
//                   // theList.add(data.getString(1));
//                   // number = number+data.getString(1)+(data.isLast()?"":";");
//                    call();
//                }
//                if (!theList.isEmpty()){
//                   theList.add(data.getString(1));
//                   number = number+data.getString(1)+(data.isLast()?"":";");
//                  //  number = number+data.isLast();
//                    sendSms(msg,number,true);
//                }
//            }
//    }
//
//
//private void sendSms(String number) {
//    Intent smsIntent = new Intent();
//    smsIntent.setAction(Intent.ACTION_SENDTO);
//    Uri.parse("smsTo:"+number);;
//    smsIntent.putExtra("smsBody", "this is anurag");
//    if (ContextCompat.checkSelfPermission(getApplicationContext(),SEND_SMS)==PackageManager.PERMISSION_GRANTED){
//        startActivity(smsIntent);
//    }else
//    {
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
//            requestPermissions(new String[]{SEND_SMS},1);
//    }
//}
//public void sendSms(String number) {
//    Intent i2=new Intent();
//    i2.setAction(Intent.ACTION_SENDTO);
//    i2.setData(Uri.parse("smsto:"+number));
//    i2.putExtra("sms_body",msg); // text message
//    startActivity(i2);
//}

    private void sendSms(String msg) {
        String number=e1.getText().toString();
//        Intent smsIntent = new Intent();
//        smsIntent.setAction(Intent.ACTION_SENDTO);
//        smsIntent.setData(Uri.parse("smsto:"+number));
//        smsIntent.putExtra("smsBody", msg);
//            startActivity(smsIntent);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(e1.getText().toString(), null, msg, null, null);
        call();
    }


    private void call()
    {
        String contact_no=e1.getText().toString();
        Intent callIntent =new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+contact_no));

       if (ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            startActivity(callIntent);
        }
        else
            {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    requestPermissions(new String[]{CALL_PHONE},1);
            }
    }

    private void startTrack()
    {
        if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION )
        !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main2Activity.this,Manifest.permission.ACCESS_COARSE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
            {
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS!=null)
                {
                    double lat = locationGPS.getLatitude();
                    double lon = locationGPS.getLongitude();

                    x=String.valueOf( lat );
                    y= String.valueOf(lon );
                }
                else
                {
                    Toast.makeText(this,"Unable To Find Location",Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void onGPS()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
