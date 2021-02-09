package com.example.human_safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.safety);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.this==null){
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                finish();

            }
        },3000);
    }
}
